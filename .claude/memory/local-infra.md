---
name: local-infra
description: 本机开发基础设施（MySQL/Redis/JDK/登录账号）
metadata:
  type: reference
---

> **2026-08-19 更新**：Spring Boot 3 升级后项目根 pom 默认 Java 17，其他 JDK 版本仅作编译/测试备用。

- **MySQL**：127.0.0.1:3306，root/root，MySQL 8.4.2，业务库 `learn`（system-app 等使用，Flyway 会建表）
- **Redis**：127.0.0.1:6379，无密码。system-app bootstrap.yml 写死密码 `123456789`，运行时必须 `-Dspring.redis.password=` 覆盖为空串，否则 AUTH 失败
- **JDK**：`/Users/mshe/developer/java/`
  - **jdk-11.0.20.jdk**（默认 JAVA_HOME，**仅供 `learn/`、`module-base-core/` 编译**）
  - **jdk-17.0.17.jdk**（主用，绝大部分模块编译 + 所有应用运行）
  - jdk-21.jdk（备用，另一台电脑主用）
  - jdk1.8.0_381.jdk（备用）
- **前端登录账号**：`admin` / `admin`（实测成功）

> 另一台电脑环境（Windows）：Maven 仓库 `D:\developer\apache-maven-3.9.16\repo`，主用 JDK 21。详见 [[multi-machine-sync]]。

相关：[[maven-local-repo]]、[[backend-startup]]
