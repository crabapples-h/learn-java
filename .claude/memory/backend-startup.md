---
name: backend-startup
description: 后端 5 应用启动命令与端口、验证链路
metadata:
  type: project
---

后端 5 个可运行应用（Spring Boot 3.2.5 升级后全部可启动，含 ai-app）：

| 应用 | 端口 | 说明 |
|------|------|------|
| system-app | 19093 | 主业务，需 MySQL+Redis |
| gateway-app | 9093 | 网关，前端入口（9093 路径走 `/api/{serviceId}/**`） |
| file-upload-app | 19094 | 文件上传 |
| socket-app | 19095 | websocket |
| ai-app | 19096 | Spring AI；需排除 DataSource 自动配置，详见 [[ai-app-not-runnable]] |

**启动命令**（JDK 17 + 远程 Nacos 凭据 + Redis 覆盖）：
```bash
/Users/mshe/developer/java/jdk-17.0.17.jdk/Contents/Home/bin/java \
  -Dspring.cloud.nacos.server-addr=hw.crabapples.cn:8848 \
  -Dspring.cloud.nacos.username=nacos -Dspring.cloud.nacos.password=BestLoveBaby! \
  -Dspring.redis.host=127.0.0.1 -Dspring.redis.password= \
  -jar modules-application/<app>/target/<app>-1.0-SNAPSHOT.jar
```

参数说明：
- system-app 必须加 Redis 覆盖（`bootstrap.yml` 写死 192.168.31.166:6379 + 密码 123456789）
- **gateway-app 必须加** `-Dspring.cloud.compatibility-verifier.enabled=false`（Boot 3.2.5 不在 2023.0.1 train 兼容范围，验证器误报）
- ai-app 启动参考 [[ai-app-not-runnable]]

**验证链路**：前端 8080 → gateway 9093 → Nacos 路由 `/api/system/**` → system-app → MySQL → JWT
登录接口：`POST /api/system/login`（admin/admin）。

Nacos 凭据见 [[remote-nacos-hw]]；前端启动见 [[frontend-vue2]]；SB3 升级期间所有坑修复见 [[springboot3-upgrade-progress]]。
