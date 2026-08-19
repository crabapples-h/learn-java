# AGENTS.md

## 项目概述

多模块 Maven 学习/演示项目，用于 Java + Spring 生态实验。Group: `cn.crabapples`，Java 11，Spring Boot 2.6.13，Spring Cloud 2021.0.4，Spring Cloud Alibaba 2021.0.4.0。

## 模块布局

| 目录 | 用途 |
|------|------|
| `learn/` | 独立 Java 学习代码（多线程、IO、Redis、Kafka 等）— 非 Spring Boot 应用 |
| `design-pattern/` | 设计模式演示（单例、工厂、观察者等） |
| `module-base-core/` | 共享基础库（MyBatis-Plus、安全、工具类），被 `modules/` 下的子模块依赖 |
| `modules/` | 库模块：`module-system`、`module-ai`、`module-file-upload`、`module-gateway`、`module-socket` |
| `modules-application/` | 可部署的 Spring Boot 应用：`system-app`、`ai-app`、`file-upload-app`、`gateway-app`、`socket-app` |
| `frontend/` | Vue2、Vue3、Electron、mpvue 项目 — 通过 `frontend/pom.xml` 构建。React 模块已注释掉 |
| `plugins/` | 可复用插件：`turing-api`、`mail-sender`、`code-generator` |
| `learn-byte-buddy/` | ByteBuddy 实验（packaging=pom，多模块） |
| `redis-server/` | 内嵌 Redis 服务器 — 独立模块，不在根 Maven reactor 中 |
| `camunda-server/` | 独立 Camunda BPM 应用 — 使用 **Spring Boot 3.5.5**，不参与主构建 |
| `docker-compose/` | 本地基础设施：Redis sentinel/cluster、Nacos 集群、Sentinel dashboard |

## 构建与运行

```bash
# 完整构建（跳过前端）
mvn clean install -pl learn,design-pattern,plugins,learn-byte-buddy,module-base-core,modules,modules-application

# 构建单个模块（含依赖）
mvn clean install -pl modules-application/system-app -am

# 指定 profile（默认为 mac）
mvn clean install -P mac    # 或 windows, linux

# 运行单个测试
mvn test -Dtest=ClassName#method -pl <module>

# modules-application 默认跳过测试，需显式开启：
mvn test -DskipTests=false -pl modules-application/system-app

# 前端构建（Vue3 — lockfile 被 gitignore，每次需重新安装）
cd frontend/frontend-vue3 && npm install && npm run build
```

## 关键约束

- **Java 11** 必需（根 pom.xml `<java.version>11</java.version>`）
- Maven profile 控制环境配置（主要是 `nacos.server-addr`），默认 profile 为 `mac`（localhost:8848）
- `module-base-core` 的父 POM 是 `modules`，不是 root — 注意层级关系
- `modules/` = 库代码，`modules-application/` = 可部署应用 — 命名相似但职责不同
- `learn/` 是独立代码片段，不属于 Spring Cloud 应用结构
- `BUG.md` 记录已知的前端/ORM 问题 — 修改相关模块前先查看

## 注意事项

- `lib/` → `BOOT-INF/lib/`：本地 JAR 通过 build resources 打包进 fat JAR，直接放 `lib/` 目录即可，无需 install 到本地 Maven 仓库
- MyBatis mapper XML 文件放在 `src/main/java`（与 mapper 接口同目录），不在 `src/main/resources` — 资源过滤会复制 `src/main/java` 下的 `**/*.xml|json|ftl`
- `modules-application` 的 maven-resources-plugin 排除了二进制文件过滤（woff/ttf/pdf/mp4/…）— 新增二进制资源类型时需加入 `nonFilteredFileExtensions` 列表，否则文件会损坏
- MyBatis-Plus 是当前使用的 ORM；MyBatis-Flex 已注释掉
- 依赖 Spring Cloud Alibaba（Sentinel、Nacos）— 部分功能需要运行基础设施
- `BUG.md` 显示所有已记录问题均已解决
