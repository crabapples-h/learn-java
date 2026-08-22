---
name: docker-compose
description: 根目录 docker-compose.yml 一站式开发编排（基础设施 + 5 个微服务）
metadata:
  type: project
---

# docker-compose 全栈编排

> 创建于 2026-08-20。位置：项目根 `docker-compose.yml`（9 个服务，桥接网络 `crabapples` / 子网 `172.20.0.0/16`）

## 包含服务

| 服务 | 镜像 | 端口 | 用途 |
|------|------|------|------|
| **mysql** | mysql:8.4.0 | 3306 | 主库（root/root，库 `learn`） |
| **nacos** | nacos-server:v2.4.3 | 8848/9848 | 注册中心 + 配置中心（鉴权 nacos/BestLoveBaby!） |
| **redis** | redis:7.2-alpine | 6379 | 缓存（无密码，maxmemory 256MB） |
| **sentinel-dashboard** | openjdk:17-jdk-slim | 8080 | Sentinel 控制台 |
| **gateway-app** | eclipse-temurin:17-jre | 9093 | 网关（前端入口） |
| **system-app** | eclipse-temurin:17-jre | 19093 | 主业务 |
| **socket-app** | eclipse-temurin:17-jre | 19095 | WebSocket |
| **file-upload-app** | eclipse-temurin:17-jre | 19094 | 文件上传 |
| **ai-app** | eclipse-temurin:17-jre | 19096 | Spring AI |

## 关键设计

- **网络**：所有服务接入 `crabapples` 桥接网络（172.20.0.0/16），服务间通过 `crabapples-nacos:8848` 等容器名访问
- **鉴权**：Nacos `NACOS_AUTH_ENABLE=true`，应用注入 `-Dspring.cloud.nacos.username=nacos -Dspring.cloud.nacos.password=BestLoveBaby!`
- **依赖顺序**：
  - `mysql` 用 healthcheck 探测 `mysqladmin ping`
  - `redis` 用 healthcheck 探测 `redis-cli ping`
  - 微服务 `depends_on: nacos(service_started)`，system-app 额外依赖 mysql/redis 健康
- **运行时参数**（JAVA_OPTS 注入）：
  - gateway 必须加 `-Dspring.cloud.compatibility-verifier.enabled=false`（Boot 3.2.5 不在 2023.0.1 train 范围）
  - system-app 需覆盖 Redis（`spring.redis.host=crabapples-redis, password=`）和 MySQL（`spring.datasource.url=jdbc:mysql://crabapples-mysql:3306/learn`）

## 启动 / 停止

```bash
# 前置：先构建所有 5 个应用的 fat jar
JAVA_HOME=/Users/mshe/developer/java/jdk-17.0.17.jdk/Contents/Home \
mvn clean install -pl modules-application/system-app,modules-application/gateway-app,modules-application/ai-app,modules-application/file-upload-app,modules-application/socket-app -am -Dmaven.test.skip=true

# 启动（先启动基础设施，再启动应用）
docker compose up -d mysql nacos redis sentinel-dashboard
# 等 nacos 启动后（约 30s）导入配置（api-system.yaml 等）
# 然后启动应用
docker compose up -d system-app gateway-app socket-app file-upload-app ai-app

# 查看日志
docker compose logs -f system-app

# 停止
docker compose down
```

## 已有 compose 资源（参考用，未合并）

- `docker-compose/nacos/docker-compose-nacos.yml` — 3 节点 Nacos 集群（需外部 MySQL@192.168.31.60，本机不可达）
- `docker-compose/redis/docker-compose-redis.yml` — Redis Sentinel
- `docker-compose/redis/docker-compose-redis-cluster-mac.yml` — Redis 集群
- `docker-compose/sentinel-dashboard/` — Sentinel 1.8.9 jar + Dockerfile

## 验证

```bash
docker compose config -q  # 语法验证
docker compose config --services  # 列出 9 个服务
```

## 相关

- [[backend-startup]]（宿主机启动方式）
- [[remote-nacos-hw]]（Nacos 凭据）
- [[local-infra]]（本机基础设施）
- [[multi-machine-sync]]（构建产物）
