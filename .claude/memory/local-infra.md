---
name: local-infra
description: 本机开发基础设施（MySQL/Redis/JDK/登录账号）
metadata:
  type: reference
---

- **MySQL**：127.0.0.1:3306，root/root，MySQL 8.4.2，业务库 `learn`（system-app 等使用，Flyway 会建表）
- **Redis**：127.0.0.1:6379，无密码。system-app bootstrap.yml 写死密码 `123456789`，运行时必须 `-Dspring.redis.password=` 覆盖为空串，否则 AUTH 失败
- **JDK**：`/Users/mshe/developer/java/` 下默认 11（jdk-11.0.20.jdk），另有 jdk-17.0.17.jdk、jdk-21.jdk、jdk1.8.0_381.jdk
- **前端登录账号**：`admin` / `admin`（实测成功）

相关：[[maven-local-repo]]、[[backend-startup]]
