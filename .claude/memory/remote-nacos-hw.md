---
name: remote-nacos-hw
description: 远程 Nacos hw.crabapples.cn 及凭据，应用启动必须注入用户名密码
metadata:
  type: reference
---

远程 Nacos：`hw.crabapples.cn:8848`
- 用户名：`nacos`
- 密码：`BestLoveBaby!`
- 命名空间 namespace：**`namespace-learn-java`**（2026-08-22 起，旧值 `3dc5f842-...` 已废弃；由根 pom.xml 各 profile 的 `@nacos.namespace@` 注入）
  - 其余 namespace：`namespace-sentinel`（Sentinel 规则）、`namespace-lock-app`（lock-app）

应用启动必须注入 `-Dspring.cloud.nacos.username=nacos -Dspring.cloud.nacos.password=BestLoveBaby!`，否则登录返回 403 "user not found!"，配置加载失败应用起不来（system-app 会因缺 `previewAddress` 等占位符崩溃）。配置（如 `api-system.yaml`、`ai-service.yaml`）都写在 `namespace-learn-java` 的 DEFAULT_GROUP 下。

网关路由：discovery-locator 模式 `/api/{serviceId}/**`，服务名 system/file/stream/api-gateway/webflux-sse。启动方式见 [[backend-startup]]。
