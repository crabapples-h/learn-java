# frontend-vue3 重构（已完成）

> 生成：2026-08-19；**全部完成**（2026-08-22）。目标：vue3 对齐 vue2 全部功能。

## 完成项（历史进度）

- ✅ **P0**：Apis.js/CommonApi.js 路径全改为 `/api/system/*`；v-auth 全局指令注册（`utils/permission.js` + main.js `app.use(permission)`）
- ✅ **P1**：C-PageMenus 搜索 + filteredMenus 递归过滤 + 展开状态维护；退出登录、路由守卫、刷新路由重建
- ✅ **P2**：19 个 manage 页面 + 3 公共组件（c-pop-button/c-file-upload-v2/c-icon-select）+ `utils/useSystem.js`
- ✅ **布局修复**：`dynamic-layout` 组件改用 `ManageIndex`（Index.vue），否则无菜单/header/footer
- ✅ **刷新 404 修复**：catch-all 重定向到 /404 发生在守卫前（守卫拿到 `to.path=/404`、`redirectedFrom=原始路径`）；守卫恢复动态路由先于白名单检查 + `next({path: to.redirectedFrom?.path, replace:true})` 回跳
- ✅ **列表自动加载**：useSystem.js `onMounted` 自动 `getList()`（有 `url.list` 时）

## SSE 5 种方式（SseExample.vue，2026-08-22）

| # | 方式 | 接口 | 请求头 |
|---|------|------|--------|
| 1 | 原生 EventSource | `/api/stream/sse/connect/unAuth` | ❌ 无法自定义头 |
| 2 | `@microsoft/fetch-event-source` | `/auth` | ✅ token |
| 3 | `sse.js`（XHR 实现） | `/auth` | ✅ token |
| 4 | axios onDownloadProgress | `/auth` | ✅ token |
| 5 | WebFlux Flux\<ServerSentEvent\> | `/api/webflux-sse/sse/connect/timer` | — |

- 包：`event-source-polyfill`（停更）→ `@microsoft/fetch-event-source@^2.0.1` + `sse.js@^2.8.0`
- **SSE 500 修复**：原生 EventSource 无法带 token 却连 `/auth` → 401 + 异常处理器对 `text/event-stream` 无法序列化 → 500；改用 `/unAuth`；`JwtInterceptor` 支持 URL 查询参数 token 兜底
- 后端 WebFlux 见 [[webflux-sse-app]]

## 技术栈与坑

- 技术栈：Vue 3.5 + Vite 7 + Pinia 3 + Vue Router 4 + Ant Design Vue 4.2
- antd v1→v4：`slot="title"`→`#title`、`:visible`→`:open`、`a-icon`→iconfont `<svg><use>`
- 动态路由用 `import.meta.glob` 懒加载，filePath 必须匹配 `src/views/**`

## 遗留（继承自 vue2，非迁移引入）

- vue3 `request.js` 拦截器 + 页面 `message.error` 重复报错提示
- `sys-depart/list.vue` 的 add-item 模板被注释、`sys-tenant/detail.vue` 是死代码

相关：[[frontend-vue2]]、[[webflux-sse-app]]
