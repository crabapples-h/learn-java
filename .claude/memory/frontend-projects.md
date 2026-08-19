---
name: frontend-projects
description: frontend/ 下各前端子项目及其技术栈清单
metadata:
  type: project
---

来源：各 `frontend/*/README.md` 与 `frontend/pom.xml`。

**在 reactor 中（frontend/pom.xml 子模块）**：
- `frontend-vue2`：Vue 2.6.14 + Ant Design Vue + Vuex + Vue Router，Vue CLI 5，**当前可用前端**（见 [[frontend-vue2]]）
- `frontend-vue3`：Vue 3 + Vite + Vue Router 4 + Pinia + Ant Design Vue 4，基于 vue2 结构搭建，**Apis.js 旧路径暂不可用**
- `frontend-mpvue`：Mpvue 小程序项目（vue init mpvue/mpvue-quickstart）
- `frontend-hbuildx`：HBuilderX 相关
- `frontend-electron`：Electron 桌面应用

**不在 reactor（独立/未启用）**：
- `frontend-react`：React + TypeScript + Vite（pom 中已注释掉）
- `frontend-vue2-new`：Vue2 新模板（npm run serve）
- `learn-vue2`：Vue2 学习项目（yarn serve）
- `app`：Vue 3 + Vite 模板
- `new-year-time`：独立项目

前端构建：lockfile 被 gitignore，`npm install` 后再 build/serve。
