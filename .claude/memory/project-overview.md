---
name: project-overview
description: 项目结构总览：多模块 Maven 学习项目，modules 与 modules-application 分工
metadata:
  type: project
---

`cn.crabapples:learn-java`，Java 17（根 pom 默认），Spring Boot 3.2.5，Spring Cloud 2023.0.1，Spring Cloud Alibaba 2023.0.3.2。Reactor 模块：learn、design-pattern、frontend、plugins、learn-byte-buddy、module-base-core、modules、modules-application。

- `modules/` = 库模块（module-system/ai/gateway/socket/file-upload/webflux-sse）；`modules-application/` = 可部署 Spring Boot 应用（system-app/gateway-app/ai-app/file-upload-app/socket-app/webflux-sse-app）——命名相似但职责不同
- `module-base-core` 父 POM 是 `modules` 而非 root
- `learn/` 是独立 Java 学习代码，非 Spring Boot 应用
- 默认 Maven profile 为 `mac`（activeByDefault，nacos **hw.crabapples.cn:8848**，namespace `namespace-learn-java`）——远程 Nacos，非本地
- `modules-application` surefire `skipTests=true`
- `lib/` 本地 JAR 通过 build resources 打包进 fat JAR 的 BOOT-INF/lib
- 前端 lockfile（package-lock.json/yarn.lock）被 gitignore，构建前必须 `npm install`
- `frontend-react` 模块在 `frontend/pom.xml` 中已注释掉
- `redis-server`、`camunda-server`（Boot 3.5.5）不在根 reactor 中
- **认证方式**：自定义 `JwtInterceptor`（HandlerInterceptor，`JwtInterceptorConfigure` 实现 `WebMvcConfigurer` 注册，拦截 `/api/**`），**没有 Spring Security / WebSecurityConfigurerAdapter**——「Security 重构」任务不适用

相关：[[pom-build-gotchas]]、[[backend-startup]]、[[build-requires-jdk17]]、[[frontend-projects]]、[[known-security-issues]]

## 文档/学习资料位置

- `document/JVM.md` — JVM 笔记（见 [[learning-jvm]]）
- `design-pattern/src/main/java/{create,structure,doing}/*.md` — 设计模式清单（见 [[learning-design-patterns]]）
- `learn/src/main/java/spring/README.md` — Spring 生命周期/三级缓存（见 [[learning-spring-lifecycle]]）
- `learn/src/main/java/elasticsearch/README.md` — ES 笔记（见 [[learning-elasticsearch]]）
- `learn/src/main/java/demo/mq/README.md` — RabbitMQ 笔记（见 [[learning-mq-rabbitmq]]）
- `learn-byte-buddy/src/test/java/base/README.md` — ByteBuddy 笔记（见 [[learning-bytebuddy]]）
- `frontend/frontend-vue2/前端代码分析报告.md`、`modules-application/system-app/Bug分析报告.md` — 安全分析（见 [[known-security-issues]]）
- `README.md` — 按时间倒序的更新日志；`CLAUDE.md`/`BUG.md` — 项目指南
