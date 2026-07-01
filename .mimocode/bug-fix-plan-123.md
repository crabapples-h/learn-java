# BUG #1-3 修复方案

基于 frontend-vue2 代码调查，以下为三个前端 bug 的根因分析和修复计划。

---

## BUG #1：菜单展开状态时重新加载后需要点击两次才能显示子菜单

### 根因

`C-PageMenus.vue` 第 4 行使用 `:default-open-keys="openMenuIds"`（非受控模式），而菜单数据通过 `store.dispatch('MENUS_TREE')` 异步加载。首次渲染时菜单数据为空，`default-open-keys` 已初始化内部 state；数据到达后组件重渲染但 `<a-menu>` 内部 state 不再响应 props 变化。

### 修复文件

- `frontend/frontend-vue2/src/views/common/C-PageMenus.vue`：添加 `@openChange` 事件监听，维护本地 `currentOpenKeys` 状态
- `frontend/frontend-vue2/src/store/modules/permissions.js`：修复 `menusTree` 初始值（空字符串 → 空数组）

### 修复步骤

1. `permissions.js` 第 16 行：`menusTree: ''` → `menusTree: []`
2. `C-PageMenus.vue`：添加 `data()` 返回 `currentOpenKeys: []`，监听 `@openChange` 同步状态
3. `openMenuIds` computed 增加优先读取 `currentOpenKeys` 的逻辑

---

## BUG #2：菜单搜索功能未完善

### 根因

`C-PageMenus.vue` 中完全没有搜索相关代码——无输入框、无过滤逻辑。

### 修复文件

- `frontend/frontend-vue2/src/views/common/C-PageMenus.vue`：新增搜索输入框 + `filteredMenus` computed

### 修复步骤

1. 在 `<a-layout-sider>` 内、`<a-menu>` 前插入 `<a-input>` 搜索框
2. 新增 `searchKey` data 属性
3. 新增 `filteredMenus` computed：递归过滤菜单树，匹配 name 关键字
4. 模板中 `v-for="item in menus"` 改为 `v-for="item in filteredMenus"`
5. 搜索激活时自动展开匹配的父菜单

---

## BUG #3：登陆超时时刷新页面会报错

### 根因

`request.js` 第 59 行 `error.response.status` 在网络超时时 `error.response` 为 `undefined`，导致 `TypeError`。且 error handler 中没有清除 token 或跳转登录页的逻辑。

### 修复文件

- `frontend/frontend-vue2/src/utils/request.js`：修复 error handler 空值检查 + 添加 HTTP 401 处理

### 修复步骤

1. error handler 中增加 `error.response` 空值检查
2. 超时（`ECONNABORTED`）和网络错误分别提示
3. HTTP 401 时执行 `storage.logout()` + 跳转 `/login`
4. 添加防重入机制，避免 6 个并发请求同时触发跳转

---

## 修复顺序

**BUG #3 → BUG #1 → BUG #2**

BUG #3 是最高优先级——超时报错影响刷新后的初始化流程，而刷新流程又是 BUG #1 的复现条件。
