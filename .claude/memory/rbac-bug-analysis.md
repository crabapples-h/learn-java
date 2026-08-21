---
name: rbac-bug-analysis
description: 后端 角色-权限-菜单 三模块关联逻辑 + 隐藏 bug 清单（2026-08-20 代码审查）
metadata:
  type: project
---

# 角色 / 权限 / 菜单模块 Bug 分析报告

> 审查时间：2026-08-20  
> 范围：`modules/module-system/.../{sysUser,sysRole,sysMenu,sysUserRole,sysRoleMenu}`

## 一、模块关联总览

### 数据模型

```
SysUser (用户表)
   │  @TableField(exist=false) @JoinField
   │  List<String> roleList  ← 通过 sys_user_roles 关联
   ▼
sys_user_roles (用户-角色关联表)
   │  user_id, role_id
   ▼
SysRole (角色表)
   │  @TableField(exist=false)
   │  List<SysMenu> menuList  ← 内存中组装，未在表里持久化
   ▼
sys_role_menus (角色-菜单关联表)
   │  role_id, menu_id
   ▼
SysMenu (菜单/权限表)
      permission 字段 = 按钮权限标识
      menusType  = 1目录 / 2菜单 / 3外部链接 / 4按钮
```

### 三个 Controller + Service 关系

| Controller | 路径前缀 | 职责 |
|------------|----------|------|
| `SystemMenuController` | `/api/system/menu` | 菜单 CRUD（save / remove） |
| `SystemMenuListController` | `/api/system/menu/list` | 列表/分页/角色菜单 |
| `SystemMenuTreeController` | `/api/system/menu/tree` | 菜单树 |
| `SystemRoleController` | `/api/system/role` | 角色 CRUD |
| `SystemUserController` | `/api/system/user` | 用户 CRUD |
| `SystemController` | `/api/system/` | 登录/权限/菜单/登出 |

---

## 二、🐛 已确认的 Bug（按严重度）

### 🔴 P0-1：JwtInterceptor 不验证用户状态 → 锁定/删除用户后仍可访问

`module-base-core/.../jwt/JwtInterceptor.java:56-58`：
```java
String userId = jwtTokenUtils.getUserId(token);
log.debug("token所属用户:[{}]", userId);
return true;   // ← 仅解出 userId 就放行
```
**影响**：已锁定（`status=1`）、已删除（`delFlag=1`）、已改密码的旧 token 仍能使用——**安全报告 [[known-security-issues]] 已标为"中风险"**。
**修复**：拦截器应 `userDAO.findById(userId)` 校验 `status==0 && delFlag==0`，失败抛 401。

### 🔴 P0-2：删除菜单/角色时未级联清理关联表 → 孤儿数据

| 接口 | 缺失清理 |
|------|---------|
| `DELETE /api/system/menu/remove/{id}`（`SystemMenuController:80-87`） | 删菜单后 **未删** `sys_role_menus` 中对应记录 |
| `DELETE /api/system/role/remove/{id}`（`SystemRoleController:106-111`） | 删角色后 **未删** `sys_user_roles` 和 `sys_role_menus` |

**对比**：`SystemMenusServiceImpl.saveMenus` 在添加子菜单时**主动**调用了 `roleMenusService.delByMenuId(form.getPid())`（line 82），证明作者**知道要级联清理**，但**删除时漏写**。
**影响**：数据库出现 `role_id/menu_id` 指向不存在主键的孤儿记录，下次保存/查询可能报错。
**修复**：
- `menusService.removeMenus` → 删前先 `roleMenusService.delByMenuId(id)`
- `rolesService.removeRoles` → 删前先 `userRolesDAO.deleteUserRoles(id)` + `roleMenusService.deleteRoleMenus(id)`

### 🔴 P0-3：`getUserMenusTree` 全表扫描所有菜单（性能/越权隐患）

`SystemMenusServiceImpl.getUserMenusTree`（line 60-70）：
```java
List<SysMenu> allRootMenuTree = systemMenusDAO.findMenusTreeList();  // 拉全量
List<SysMenu> list = filterRootMenusTree(userMenuIds, allRootMenuTree);  // 内存过滤
```
**问题**：
1. **全表拉**——任何登录用户都先 `select * from sys_menu` 把所有菜单（含其他租户的）拉进内存，**毫无租户隔离**（SysMenu 实体无 `tenantId`，`SysUser`/`SysRole` 有但未使用）
2. **N+1 递归**——`MenusMapper.xml` 的 `findMenusTree` 用 MyBatis collection 递归，每个菜单节点都触发一次子查询
3. **超大系统下必慢**——10 万条菜单 → 10 万次 SQL

**对比**：已有 `MenusMapper.xml:82` 的 `getUserMenus` 用 `INNER JOIN sys_user_roles / sys_role_menus / sys_menu WHERE user_id=?` 一次拿到该用户所有菜单，**应直接用这个**。`filterRootMenusTree` 仅用于按 pid 拼树，没必要先全表再过滤。

### 🟠 P1-1：`saveRoleMenus` 失败回滚缺失 → 部分写入

`RoleMenusDAO.saveRoleMenus`（line 83-89）：
```java
mapper.deleteRoleMenus(id);     // 删
menusList.forEach(e -> {         // 逐条插
    mapper.saveRoleMenus(id, e);
});
```
**没有 `@Transactional`！** 中间任何一条 insert 失败，**前面的 insert 不会回滚**（delete 已提交）。
**修复**：在 `RoleMenusDAO.saveRoleMenus` 加上 `@Transactional(rollbackFor=Exception.class)`；同理 `UserRolesDAO.saveUserRoles`（line 17-22）。

### 🟠 P1-2：`SysUser` 用 `String id` 但用 MyBatis-Plus 自增主键策略

`SysUser` 类：
```java
@Id
private String id;     // String 类型
```
`UserMapper.xml` / mybatis-generator.xml：
```xml
<generatedKey column="id" sqlStatement="Mysql" identity="true"/>
```
`id` 字段 `String` 但 MySQL 列是自增 INT/BIGINT → MyBatis-Plus 默认雪花算法（19 位字符串）会与 DB 期望冲突。
**当前能跑**是依赖 base64/snowflake 自定义 IdType，但 `String` 配 `identity=true` 是反模式。
**对比**：`SysRole.id` 也是 `String`，但 role 没有 generatedKey 配置。

### 🟡 P2-1：菜单保存后权限变化不会立即生效（缓存缺失）

`SysMenuController.saveMenus`（`SystemMenuController:64-71`）：
- 改菜单的 `permission` 字段后
- 已登录用户持有的 JWT 仍带旧 permission 列表
- `getUserPermissions`（`SystemServiceImpl:103-112`）每次实时查 DB 算权限，**这里没缓存问题**
- 但**菜单树/路由** 前端用 localStorage 缓存了 `OPEN_MENU_IDS`、`SELECT_MENU_IDS`，**改菜单结构后用户不重新登录就看不到变化**
- **建议**：菜单/角色变更后**广播失效事件**（或用 Spring Cache + Redis publish）

### 🟡 P2-2：`getChildList` 路径冲突

`SystemMenuController.getChildList(String pid)`（line 48-55）和 `MenusMapper.findMenusList`：
```xml
<select id="findMenusList">
  select ... from sys_menu a
  left join sys_menu b on a.id = b.pid
  where a.pid = #{id} and a.del_flag = 0
</select>
```
**问题**：当 `pid=null` 时（顶层菜单）→ `where a.pid = null` → **返回空**！调用方 `getChildList(null)` 应该返回根菜单，实际没有。
**对比**：`findMenusListPage`（line 91-98）正确处理了 `pid is null`。
**修复**：把 findMenusList 的 `where a.pid = #{id}` 改为 `<choose>` 判断 null。

### 🟡 P2-3：日志泄露敏感信息

`SystemServiceImpl.login`（line 85）：
```java
log.info("开始登录->用户名:[{}],密码:[{}]", username, password);
```
**安全报告 [[known-security-issues]] 已标 🔴**——密码以明文（或 MD5 哈希）写入日志。
**修复**：只记用户名 + 密码长度。

### 🟢 P3-1：`System.err.println(menu)` 残留调试输出

`RoleMenusDAO.addChildToParent`（line 59）：
```java
System.err.println(menu);   // ← 死代码 + 影响日志格式
```
**修复**：删除。

### 🟢 P3-2：`SysMenu.path` 字段映射重复

`MenusMapper.xml:13-15`：
```xml
<result property="path" column="path"/>
<result property="path" column="path"/>   <!-- 重复 -->
```
**影响**：无害（XML 解析忽略重复），但 XML 不规范。
**修复**：删一行。

### 🟢 P3-3：`BaseForm<SysRole>.toEntity()` 反射赋值可能越权

`RolesForm` 含 `menuList: List<String>`（line 27），`toEntity()` 用 `BeanUtils.copyProperties` 复制到 `SysRole`，**但 `SysRole.menuList` 是 `@TableField(exist=false)`**——MyBatis-Plus 不会写入。
**结果**：看似"保存角色菜单"，实际只更新角色表，菜单靠 `roleMenusService.saveRoleMenus`（`SystemRolesServiceImpl.saveRoles:97-99`）手动维护。**逻辑正确但易混淆**——开发者加新字段时容易踩坑。
**建议**：移除 `SysRole.menuList` 字段，或在 Form 显式分离 role 字段 vs menuIds 字段。

### 🟢 P3-4：`SysRole` 无 `delFlag` 字段

`SysUser` 有 `@TableLogic delFlag` + `status`（禁用标志），`SysMenu` 有 `@TableLogic delFlag`，
但 **`SysRole` 都没有**！
- 删角色时 `rolesDAO.deleteById(id)`（`SystemRoleController:106-111`）走 MyBatis-Plus 物理删除
- 不能"软删除"角色、不能"禁用"角色
- 已有 `sys_user_roles` 关联的角色被物理删除 → 用户失去角色无感知

**修复**：加 `@TableLogic delFlag` + `status`。

### 🟢 P3-5：`SystemController.getUserMenusIds` 是死代码

`SystemServiceImpl:117-133` 定义了 `getUserMenusIds()` 但**没有 Controller 调用它**——实际菜单获取走的是 `getUserMenusTree`。
**修复**：删除。

### 🟢 P3-6：N+1 查询隐患

`SystemMenusServiceImpl.getUserMenusTree`（line 60-70）→ `MenusMapper.findMenusTree` 用 MyBatis `<collection select="findMenusTree">` 递归。
每个菜单节点都触发一次 `select * from sys_menu where pid=?`，**典型的 N+1**。
**修复**：改用 `findMenusTree1`（已存在的 `WITH RECURSIVE` 写法，line 70-80），但目前**没人调用** `findMenusTree1`！

---

## 三、🔐 数据完整性建议

### 1. `sys_user_roles` 缺少数据库层外键

- 当前依赖应用层 `deleteUserRoles` 先删再插
- 建议加 `FOREIGN KEY (user_id) REFERENCES sys_user(id) ON DELETE CASCADE`
- `sys_role_menus` 同理

### 2. 角色/菜单缺租户隔离

- `SysUser`、`SysRole` 有 `tenantId: String`（多租户用逗号分隔，**反模式**）
- `SysMenu` **无 tenantId**！
- 任何用户能拉所有租户的菜单 → 多租户安全漏洞
- 建议：Menu 增加 tenantId，所有查询按 `tenant_id` 过滤

### 3. `permission` 字段无唯一约束

- 同一系统内 `sys_user:list` 标识理论上不应重复
- 无唯一约束 → 删除/编辑时易冲突
- 建议 DB 层 `UNIQUE KEY uk_permission`

---

## 四、修复优先级总结

| 优先级 | 数量 | 关键项 |
|--------|------|--------|
| 🔴 P0 | 3 | JwtInterceptor 状态校验、级联删除、菜单全表扫描 |
| 🟠 P1 | 2 | 事务回滚、ID 类型一致性 |
| 🟡 P2 | 3 | 缓存失效、getChildList null、日志密码 |
| 🟢 P3 | 6 | 调试代码、字段重复、软删除、死代码、N+1 |

**最优先修**：P0-1（安全）、P0-2（数据完整性）、P0-3（性能+越权）。

---

## 五、修复状态（2026-08-20）

| 优先级 | 状态 | 修复内容 |
|--------|------|---------|
| 🔴 P0-1 | ✅ 已修复 | JwtInterceptor 用户态校验（接口反转模式，UserAuthChecker 3个文件） |
| 🔴 P0-2 | ✅ 已修复 | 删除菜单/角色级联清理（RoleMenusMapper.delByRoleId，removeMenus/removeRoles 加级联删） |
| 🔴 P0-3 | ✅ 已修复 | getUserMenusTree N+1 查询（findAllMenusFlat 一次全量查 + Java 层 buildMenuTree 组装树） |
| 🟠 P1 | ✅ 已修复 | saveRoleMenus/saveUserRoles 加 @Transactional(rollbackFor=Exception.class) |
| 🟡 P2 | ⏳ 待修复 | 缓存失效、getChildList null、日志密码 |
| 🟢 P3 | ⏳ 待修复 | 调试代码、字段重复、软删除、死代码 |

## 五、相关记忆

- [[known-security-issues]]（JWT/MD5/CSRF 等已记录安全风险）
- [[multi-datasource]]（多租户相关）
