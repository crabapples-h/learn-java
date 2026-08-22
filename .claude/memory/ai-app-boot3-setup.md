---
name: ai-app-boot3-setup
description: ai-app 启动注意（Boot2 起不来；Boot3 配置走 Nacos 的 ai-service.yaml，需排除 DataSource）
metadata:
  type: project
---

`modules-application/ai-app`（Spring AI 1.0.0-M6，端口 19096）：
- **Boot 2.x**：无法启动（`OpenAiChatModel` bean 缺失，作者注明"springboot2.x版本无法启动,后续更换为3.x版本"）
- **Boot 3.2.5（当前）**：✅ **已可启动**（2026-08-22 实测）。配置分工：
  1. **本地 `bootstrap.yml`**（只放非敏感/环境配置）：
     - `spring.config.import: optional:nacos:${spring.application.name}.yaml` → 拉取 Nacos 的 `ai-service.yaml`
     - 排除数据源自动配置（否则报 `Failed to configure a DataSource`）：
       ```yaml
       spring:
         autoconfigure:
           exclude:
             - org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
             - com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration
       ```
       原因：`module-ai` 依赖 `module-base-core`（含 mybatis-plus → spring-boot-starter-jdbc），但 ai-app 不使用数据库。
     - Nacos 用 Maven 占位符：`server-addr: @nacos.server-addr@`、`config.namespace: @nacos.namespace@`、`username/password: @nacos.username@/@nacos.password@`
  2. **Nacos 配置中心 `namespace-learn-java` 下的 `ai-service.yaml`**（DEFAULT_GROUP）：
     - `spring.ai.openai.*`（api-key/base-url/model/temperature/embedding.enabled=false）写在这里
     - ⚠️ **缩进必须正确**：曾经因 YAML 缩进错误导致 `spring.ai.openai.*` 未落在正确层级 → 报 `OpenAI API key must be set`。日志显示 `Load config[dataId=ai-service.yaml] success` 并不代表属性解析正确，需检查 YAML 层级
- **必须 `spring-cloud-starter-bootstrap` 依赖**（`module-ai/pom.xml`）：Spring Boot 3 默认禁用 bootstrap context，没有它 `bootstrap.yml` 不生效 → 报 `No spring.config.import property has been defined`
- **注意**：Windows 上 `mvn clean` 前必须先停掉运行中的 jar，否则 target 被锁定导致构建失败

相关：[[multi-machine-sync]]、[[pom-build-gotchas]]
