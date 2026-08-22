---
name: frontend-vue2
description: frontend-vue2 是稳定可用前端（端口8080）；frontend-vue3 已迁移完成（端口5173，主推）
metadata:
  type: project
---

后端对应**两个可用前端**：

**frontend-vue2**（稳定，最早可用）：
- 启动：`cd frontend/frontend-vue2 && npm run serve`（node_modules 已装），端口 8080，devServer 代理 `/api` → http://localhost:9093（网关）
- `src/api/Apis.js` 路径与后端一致（`/api/system/*`）

**frontend-vue3**（2026-08-22 已迁移完成，主推）：
- 启动：`cd frontend/frontend-vue3 && npm run dev`（vite，端口 5173，代理 `/api` → 网关 9093）
- P0（路径 `/api/system/*` + v-auth）、P1（菜单搜索/展开、路由守卫）、P2（19 管理页面 + 组件）、SSE 5 种方式均已完成
- 详情见 [[vue3-migration-plan]]

相关：[[frontend-projects]]、[[vue3-migration-plan]]
