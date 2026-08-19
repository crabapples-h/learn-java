---
name: backend-startup
description: 后端 4 应用启动命令与端口、验证链路
metadata:
  type: project
---

后端 4 个可运行应用（ai-app 除外，见 [[ai-app-not-runnable]]）：

| 应用 | 端口 | 说明 |
|------|------|------|
| system-app | 19093 | 主业务，需 MySQL+Redis |
| gateway-app | 9093 | 网关，前端入口 |
| file-upload-app | 19094 | 文件上传 |
| socket-app | 19095 | websocket |

启动命令（JDK 17 + 远程 Nacos 凭据 + Redis 覆盖）：
```
/Users/mshe/developer/java/jdk-17.0.17.jdk/Contents/Home/bin/java \
  -Dspring.cloud.nacos.server-addr=hw.crabapples.cn:8848 \
  -Dspring.cloud.nacos.username=nacos -Dspring.cloud.nacos.password=BestLoveBaby! \
  -Dspring.redis.host=127.0.0.1 -Dspring.redis.password= \
  -jar modules-application/<app>/target/<app>-1.0-SNAPSHOT.jar
```
（仅 system-app 需要 Redis 覆盖参数，其他 app 无需。）

验证链路：前端 8080 → gateway 9093 → Nacos 路由 `/api/system/**` → system-app → MySQL → JWT。登录 `POST /api/system/login`（admin/admin）。Nacos 见 [[remote-nacos-hw]]，前端见 [[frontend-vue2]]。
