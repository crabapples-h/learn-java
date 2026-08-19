---
name: remote-nacos-hw
description: 远程 Nacos hw.crabapples.cn 及凭据，应用启动必须注入用户名密码
metadata:
  type: reference
---

远程 Nacos：`hw.crabapples.cn:8848`
- 用户名：`nacos`
- 密码：`BestLoveBaby!`
- 命名空间 namespace：`3dc5f842-09a4-4db0-b6ee-f4aa95199c71`

应用启动必须注入 `-Dspring.cloud.nacos.username=nacos -Dspring.cloud.nacos.password=BestLoveBaby!`，否则登录返回 403 "user not found!"，配置加载失败应用起不来（system-app 会因缺 `previewAddress` 等占位符崩溃）。

网关路由：discovery-locator 模式 `/api/{serviceId}/**`，服务名 system/file/stream/api-gateway。启动方式见 [[backend-startup]]。
