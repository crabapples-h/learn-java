# frontend-vue3 重构规划

> 生成时间：2026-08-19  
> 目标：基于现有 vue3 代码基础，实现对齐 frontend-vue2 的全部功能

> **进度更新（2026-08-19）**：
> - ✅ **P0 已完成**：Apis.js/CommonApi.js 路径全部改为 `/api/system/*`（对照后端 Controller 与 vue2 权威路径）；`v-auth` 全局指令已注册（`utils/permission.js` + main.js `app.use(permission)`）
> - ✅ **P1 核心已完成**：C-PageMenus.vue 增加搜索框 + `filteredMenus` 递归过滤 + 展开状态维护（@openChange + localStorage 持久化）；退出登录、路由守卫、刷新后路由重建原本已具备
> - ✅ **构建通过**：`npm run build` 成功（vite 7.3.6）
> - ✅ **P2 已完成**（2026-08-19 子代理迁移）：19 个 manage 页面 + 3 个公共组件（c-pop-button/c-file-upload-v2/c-icon-select）+ `utils/useSystem.js` 组合式函数，`npm run build` 通过
> - ✅ **布局修复**：`router/index.js` 的 `dynamic-layout` 改用 `ManageIndex`（Index.vue）作组件（原为裸 RouterView → 无菜单/header/footer）；`sys-depart/add.vue` 的 `saveDicts` → `saveDeparts`
> - ✅ **刷新 404 修复**：catch-all 重定向到 /404 发生在守卫**之前**（守卫拿到 `to.path=/404`、`redirectedFrom=原始路径`）；守卫改为恢复动态路由先于白名单检查，并用 `next({path: to.redirectedFrom?.path, replace:true})` 回跳（Node 模拟验证通过）
> - ✅ **列表页自动加载数据**：`useSystem.js` 加 `onMounted` 自动 `getList()`（有 `url.list` 时）
> - ✅ **示例页**：迁移 `views/example/SseExample.vue`、`WebSocketExample.vue`
> - ✅ **SSE 包升级**（2026-08-21）：`event-source-polyfill`（已停更）→ **`@microsoft/fetch-event-source@^2.0.1`**（微软官方、活跃维护）；SseExample 的第二种连接方式改用 `fetchEventSource`（`onmessage` 回调 + `AbortController`），构建后 chunk 从 14.33kB 降到 4.67kB。三种方式：原生 EventSource（无请求头）/ fetchEventSource（自定义请求头）/ axios onDownloadProgress
> - ⚠️ **遗留**：vue3 `request.js` 拦截器 + 页面 `message.error` 会重复报错提示；`sys-depart/list.vue` 的 add-item 模板被注释、`sys-tenant/detail.vue` 是死代码（均继承自 vue2）

---

## 一、当前状态评估

### ✅ 已完成（可复用）
| 模块 | 状态 | 说明 |
|------|------|------|
| main.js | ✅ 完整 | createApp + pinia + router + antd |
| store/index.js | ✅ 完整 | pinia 创建并导出 |
| store/modules/user.js | ✅ 完整 | login/loadUserBaseInfo 已实现 |
| store/modules/permissions.js | ✅ 完整 | 含 formatMenusTree + initRouter 调用 |
| store/modules/roles.js | ✅ 完整 | loadRoles 已实现 |
| store/modules/sys.js | ⚠️ 待补 | 需补充 sys 模块内容 |
| router/index.js | ⚠️ 部分 | 动态路由 + 守卫框架在，但路径硬编码问题 |
| Login.vue | ⚠️ 部分 | 登录流程在，但接口路径错误 |
| Layout.vue | ✅ 基础完整 | 含 sidebar/header，需补搜索 |
| C-PageMenus.vue | ⚠️ 部分 | 菜单渲染在，缺搜索+展开状态维护 |
| C-PageHeader.vue | ⚠️ 部分 | 用户信息展示，缺退出登录 |
| App.vue | ✅ 基本完整 | 含初始化逻辑 |
| 静态路由 (404/401/login/loading) | ✅ 完整 | |

### ❌ 缺失/需重构
| 模块 | 优先级 | 说明 |
|------|--------|------|
| **Apis.js 路径** | P0 | 所有路径是 `/api/user/*` `/api/sys/*`，后端实际是 `/api/system/*` |
| **CommonApi.js 路径** | P0 | 同上，需全部对齐 |
| **v-auth 指令** | P1 | permission.js 已有 hasPermission，但未注册为全局指令 |
| **菜单搜索功能** | P1 | C-PageMenus.vue 无搜索框 |
| **菜单展开状态** | P1 | 已修复（BUG #1 在 vue2 已修复，vue3 需确认） |
| **UpdatePassword.vue** | P1 | 修改密码页面缺失 |
| **c-file-upload.vue** | P2 | 文件上传组件缺失 |
| **c-pagination.js** | P2 | 分页组件需确认 vue3 写法 |
| **视图层 views/** | P2 | sys-depart/sys-tenant/sys-dict/sys-menu 等管理页面 |
| **example 示例页** | P3 | Bluetooth/Sse/WebSocket 示例（可选） |

---

## 二、修复清单（按优先级）

### P0：核心功能阻塞（必须修）

#### 2.1 修复 API 路径
**文件：** `src/api/Apis.js` + `src/api/CommonApi.js`

| 当前（错误） | 修正为 | 对应后端 Controller |
|-------------|--------|-------------------|
| `/api/user/login` | `/api/system/login` | SystemController @PostMapping `/login` |
| `/api/user/logout` | `/api/system/logout` | SystemController @PostMapping `/logout` |
| `/api/user/info` | `/api/system/userInfo` | SystemController @GetMapping `/userInfo` |
| `/api/user/roles` | `/api/system/user/roles` | SystemUserController @GetMapping `/list`（需确认） |
| `/api/sys/menu/tree/user` | `/api/system/menu/tree/user` | SystemMenuTreeController |
| `/api/sys/menu/list/user` | `/api/system/menu/list/user` | SystemMenuListController |
| `/api/sys/permission/list/user` | `/api/system/permissions` | SystemController @GetMapping `/permissions` |
| `/api/sys/server-address` | `/api/system/server/address` | SystemController @GetMapping `/server/address` |
| `/api/sys/file-preview-address` | `/api/system/preview/address` | SystemController @GetMapping `/preview/address` |

#### 2.2 全局注册 v-auth 指令
**文件：** `src/utils/permission.js`

```javascript
import { createApp } from 'vue'
import { usePermissionStore } from '@/store/modules/permissions'
import storage from '@/store/storage'

export function hasPermission(permissions, code) {
  if (!permissions) return false
  return permissions.includes(code)
}

// 注册为全局指令
export function install(app) {
  app.directive('auth', {
    mounted(el, binding) {
      const permissionStore = usePermissionStore()
      const permissions = permissionStore.PERMISSIONS || storage.getPermissions() || []
      if (!hasPermission(permissions, binding.arg)) {
        el.parentNode?.removeChild(el)
      }
    },
    beforeMount(el, binding) {
      // 同 mounted
    }
  })
}
```

在 `main.js` 中调用 `app.use(...)` 或直接在 permission.js 导出 install。

---

### P1：功能补全

#### 2.3 C-PageMenus.vue 增强
**文件：** `src/views/common/C-PageMenus.vue`

需新增：
1. **搜索框**：`<a-input-search>` 绑定 `searchKey`，computed `filteredMenus` 递归过滤
2. **受控展开状态**：`<a-menu :default-open-keys>` 改为 `:open-keys` + `@openChange` 维护本地 state
3. **图标渲染**：vue2 用 `<svg><use xlink:href="#icon-${item.icon}"/></svg>`，vue3 保持相同写法

#### 2.4 C-PageHeader.vue 补全
**文件：** `src/views/common/C-PageHeader.vue`

需新增：
1. **退出登录按钮**：点击调用 `userStore.logout()` + 跳转 `/login`
2. **用户信息下拉**：显示用户名 + 修改密码入口

#### 2.5 UpdatePassword.vue
**文件：** `src/views/manage/UpdatePassword.vue`（新建）

从 vue2 迁移同名文件，改为 `<script setup>` 写法。

#### 2.6 Layout.vue 路由守卫适配
**文件：** `src/router/index.js` + `src/App.vue`

vue3 版本已有动态路由框架，需确认：
- 登录成功后 `initRouter` 调用时机
- 401 跳转逻辑
- 刷新页面时从 storage 恢复 token 并重新加载路由

---

### P2：组件/页面迁移

#### 2.7 迁移 vue2 组件
| vue2 源文件 | vue3 目标 | 说明 |
|------------|----------|------|
| `c-file-upload.vue` | `src/components/c-file-upload.vue` | 文件上传，需适配 antd4 |
| `c-pagination.js` | `src/utils/CPagination.js` | 分页工具，改 export function |
| `c-pop-button.vue` | `src/components/c-pop-button.vue` | 带确认的按钮 |
| `c-dict-select.vue` | `src/components/c-dict-select.vue` | 字典下拉 |
| `c-dict-radio.vue` | `src/components/c-dict-radio.vue` | 字典单选 |

#### 2.8 迁移 vue2 视图页面
从 `frontend/frontend-vue2/src/views/manage/` 迁移：
- `sys-user/` — 用户管理
- `sys-role/` — 角色管理  
- `sys-menu/` — 菜单管理
- `sys-depart/` — 部门管理
- `sys-tenant/` — 租户管理
- `sys-dict/` — 字典管理

**迁移原则：**
- 模板语法改为 vue3（`{{ }}` 不变，但 `v-model`、事件绑定语法可能变化）
- `<script>` 段改为 `<script setup>`
- `this.$store.dispatch` → `useXxxStore().action()`
- `this.$router` → `useRouter()`
- `this.$http` → 直接 import request

---

## 三、技术栈对比

| 项目 | Vue 2 | Vue 3 (当前) | 备注 |
|------|-------|-------------|------|
| 框架 | 2.6.14 | 3.5.22 | ✅ 已升级 |
| 构建 | Vue CLI 5 | Vite 7.3.5 | ✅ 已升级 |
| UI | Ant Design Vue 1.7.8 | Ant Design Vue 4.2.6 | ⚠️ 需适配 API 变化 |
| 状态 | Vuex 3.6.2 | Pinia 3.0.4 | ✅ 已迁移（部分） |
| 路由 | Vue Router 3.5.2 | Vue Router 4.6.3 | ✅ 已升级 |
| HTTP | Axios 1.11.0 | Axios 1.13.2 | ✅ 已升级 |

**Ant Design Vue 2→4 主要 API 变化：**
- `a-input` slot 改为 `#prefix` 模板语法
- `a-form` 的 `:model` + `a-form-item` 的 `prop` 绑定方式不变
- `a-menu` 的 `slot="title"` 改为 `#title` 插槽
- `a-modal` 的 `visible` 改为 `open`
- 部分组件命名变化（如 `a-button` 的 `icon` prop 位置）

---

## 四、实施顺序

```
阶段 1（P0，1天）：
  1. 修复 Apis.js + CommonApi.js 所有路径
  2. 注册 v-auth 全局指令
  3. 验证登录流程可跑通

阶段 2（P1，2天）：
  4. C-PageMenus.vue 搜索 + 展开状态
  5. C-PageHeader.vue 退出登录
  6. App.vue 路由守卫完善
  7. UpdatePassword.vue 迁移

阶段 3（P2，3天）：
  8. 迁移公共组件（c-file-upload 等）
  9. 迁移 manage 视图页面（user/role/menu/depart/tenant/dict）
  10. 联调测试

阶段 4（P3，可选）：
  11. example 示例页（SSE/WebSocket）
  12. 安全加固（RSA 密钥环境变量化、CSRF 防护）
```

---

## 五、风险点

1. **Ant Design Vue 4 兼容**：vue2 的 UI 代码改到 vue3 时，antd4 部分 API 变化需逐一排查
2. **动态路由 filePath 解析**：vue3 router 用 `import.meta.glob` 懒加载，vue2 用静态 import，需确保 filePath 字段与项目文件路径匹配
3. **Pinia vs Vuex 的 action 签名**：vuex action 第一个参数是 `{commit, state}`，pinia action 是 `this`，需改调用方式
4. **RSA 公钥硬编码**：安全分析报告指出，建议迁移到 `.env` 环境变量

---

## 六、验证标准

- [ ] 登录 `admin/admin` 成功，返回 JWT
- [ ] 登录后跳转到 `/manage/welcome`
- [ ] 左侧菜单显示动态路由，支持搜索过滤
- [ ] 菜单展开状态刷新后保持
- [ ] 权限按钮 `v-auth:sys:user:add` 正确控制显隐
- [ ] 退出登录跳转 `/login`
- [ ] 修改密码功能可用
- [ ] 用户/角色/菜单/部门/租户/字典管理页面可打开（CRUD 联调）
