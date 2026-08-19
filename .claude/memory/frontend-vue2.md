---
name: frontend-vue2
description: 前端用 frontend-vue2，vue3 的 Apis.js 是旧路径登录会 404
metadata:
  type: project
---

当前后端对应**可用的前端是 frontend-vue2**：
- 启动：`cd frontend/frontend-vue2 && npm run serve`（node_modules 已装），端口 8080，devServer 代理 `/api` → http://localhost:9093（网关）
- 其 `src/api/Apis.js` 路径与后端一致（如 `/api/system/login`）

**frontend-vue3 暂不可用**：`src/api/Apis.js` 是旧路径（`/api/user/login`、`/api/sys/menu/tree/user`），与后端 `/api/system/*` 不匹配，登录会 404。需要改 8 处路径才能用。
