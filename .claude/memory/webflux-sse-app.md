---
name: webflux-sse-app
description: WebFlux SSE 演示应用（端口19097）与 gateway discovery-locator 路由坑（filters 空→不 strip 前缀）
metadata:
  type: project
---

# WebFlux SSE 演示应用（2026-08-22 新增）

- **库模块**：`modules/module-webflux-sse`（webflux + nacos + bootstrap + lombok）
- **应用**：`modules-application/webflux-sse-app`（端口 **19097**，Netty，Nacos service 名 `webflux-sse`）
- **主类**：`cn.crabapples.webflux.WebFluxSseApplication`
- **Controller**：`WebFluxSseController`（`@RequestMapping("/api/webflux-sse/sse")`）
  - `GET /api/webflux-sse/sse/connect/{id}` — Sinks 单播建连
  - `GET /api/webflux-sse/sse/connect/timer/{id}` — Flux.interval 定时推 10 条（演示用）
  - `POST /api/webflux-sse/sse/send/{id}` — 触发推送
- **前端**：`SseExample.vue` 第 5 种方式（WebFlux Flux\<ServerSentEvent\>），走 `/api/webflux-sse/sse/connect/timer`

## ⚠️ gateway discovery-locator 路由坑（重要）

`gateway-app/bootstrap.yml` 的 `spring.cloud.gateway.discovery.locator`：
- predicate：`Path = /api/{serviceId}/**`
- **`filters` 为空**（RewritePath 被注释）→ 网关**不会自动 RewritePath strip 前缀**（注释里「自动配置 RewritePath」是误导，实际必须显式配置）
- 结果：匹配 `/api/{serviceId}/**` 后**保留前缀原样转发**到下游服务

**推论**：下游服务的 Controller 路径**必须带完整前缀 `/api/{serviceId}/...`**（与网关转发路径一致），不能用短路径：
- `system-app` controller 是 `/api/system/...` → 网关转发 `/api/system/...` ✅
- `webflux-sse` 早期写成 `/sse` → 网关转发 `/api/webflux-sse/sse/...` 匹配不到 → **404**；改成 `/api/webflux-sse/sse` 后 ✅

> 若某天启用 RewritePath strip 前缀，会**破坏现有 `/api/{serviceId}/...` 的 controller**，需整体改造，故保持「controller 用完整路径」约定。

## 启动命令（Windows + JDK21）
```bash
java -jar modules-application/webflux-sse-app/target/webflux-sse-app-1.0-SNAPSHOT.jar
```
（Nacos 地址/namespace 由 bootstrap.yml 的 Maven 占位符烘入，无需 -D 参数）

## 构建注意
- module-webflux-sse 需 lombok 依赖（非 optional，与 module-base-core 一致，否则 app 编译报 `log` 找不到）
- 已加入 `modules/pom.xml`、`modules-application/pom.xml` 的 `<modules>`

相关：[[vue3-migration-plan]]（SSE 5 方式）、[[backend-startup]]、[[pom-build-gotchas]]
