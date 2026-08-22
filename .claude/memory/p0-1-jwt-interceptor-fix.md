---
name: p0-1-jwt-interceptor-fix
description: P0-1 修复记录：JwtInterceptor 状态校验（接口反转模式）
metadata:
  type: project
---

# P0-1 修复记录：JwtInterceptor 用户态校验

> 修复时间：2026-08-20
> 问题：JwtInterceptor 只解 token 出 userId 就放行，已锁定/已删除用户的旧 token 仍可访问

## 修复方案：接口反转（避免循环依赖）

- `module-system` → `module-base-core` 单向依赖
- `module-base-core` 不能反向引用 `SysUser`/`UserDAO`（在 module-system）
- 用接口反转：base-core 定义接口，module-system 提供实现，base-core 通过接口调用

## 修改文件

### 1. 新增：`module-base-core/.../jwt/UserAuthChecker.java`
接口：
```java
public interface UserAuthChecker {
    boolean isUserActive(String userId);
}
```

### 2. 修改：`module-base-core/.../jwt/JwtInterceptor.java`
- `@Autowired(required = false) private UserAuthChecker userAuthChecker;`
- 在 `preHandle` 解析出 userId 后调用 `userAuthChecker.isUserActive(userId)`
- 失败抛 `ApplicationException("登录信息已失效，请重新登录", 401)`
- 若 classpath 没有实现（socket-app / file-upload-app / ai-app 等无用户表），userAuthChecker 为 null，跳过校验保持向后兼容

### 3. 新增：`module-system/.../sysUser/auth/SysUserAuthCheckerImpl.java`
实现，注入 UserDAO 查 SysUser：
- userId 为空 → false
- userDAO.findById 找不到 → false
- user.status == DIC.USER_LOCK（=1）→ false
- user.delFlag != DIC.NOT_DEL（!=0）→ false
- 数据库异常时**保守放行**（避免一刀切拒绝所有请求），记录错误日志
- 使用 DIC 常量避免硬编码值

## 设计要点

1. **接口位置**：放在 `module-base-core/common/jwt/UserAuthChecker.java`，与 JwtInterceptor 同包，语义关联
2. **可选注入**：`required = false`，保证非 system-app（gateway/socket/file-upload/ai）不破坏现有功能
3. **容错**：实现类捕获异常后放行（不阻断业务），同时记 ERROR 日志方便排查
4. **常量复用**：直接用 `DIC.USER_LOCK` 和 `DIC.NOT_DEL`，未来 DIC 变更自动同步

## 验证计划

- [x] 三个文件全部写入
- [x] 编译验证（2026-08-20：mvn compile 通过）
- [ ] 启动 system-app，登录后用 SQL 锁定该用户，验证 token 后续请求被拦截
- [ ] 启动 gateway/socket/file-upload/ai，验证向后兼容（无 UserAuthChecker 实现，不抛 401）

## 相关

- [[rbac-bug-analysis]] — 完整 bug 清单
- [[multi-machine-sync]] — 当前 Boot 3.2.5 状态

## 后续修复

- **P0-2**：删除菜单/角色级联清理（2026-08-20 已修复）
- **P0-3**：getUserMenusTree N+1 查询改用全量查+Java 组装树（2026-08-20 已修复）
- **P1**：saveRoleMenus/saveUserRoles 加 @Transactional（2026-08-20 已修复）
