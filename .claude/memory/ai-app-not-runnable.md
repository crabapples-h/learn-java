---
name: ai-app-not-runnable
description: ai-app 启动注意（Boot2 起不来；Boot3 已可启动，但需排除 DataSource 自动配置）
metadata:
  type: project
---

`modules-application/ai-app`（Spring AI 1.0.0-M6）：
- **Boot 2.x**：无法启动（`OpenAiChatModel` bean 缺失，作者注明"springboot2.x版本无法启动,后续更换为3.x版本"）
- **Boot 3.2.5（当前）**：✅ **已可启动**（2026-08-19 Windows + JDK 21 实测，端口 19096）。配置已改为 `application.yml`：
  - 排除数据源自动配置（否则报 `Failed to configure a DataSource`）：
    ```yaml
    spring:
      autoconfigure:
        exclude:
          - org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
          - com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration
    ```
    原因：`module-ai` 依赖 `module-base-core`（含 mybatis-plus → spring-boot-starter-jdbc），但 ai-app 本身不使用数据库。
  - Nacos 用 Maven 占位符 `@nacos.server-addr@`（与其余应用一致）；运行时需 `-Dspring.cloud.nacos.server-addr=hw.crabapples.cn:8848 -Dspring.cloud.nacos.username=nacos -Dspring.cloud.nacos.password=BestLoveBaby!`（否则连默认 127.0.0.1:8848 失败，报 `NacosException: Client not connected`）
  - **注意**：Windows 上 `mvn clean` 前必须先停掉运行中的 jar，否则 target 被锁定导致构建失败

相关：[[springboot3-upgrade-progress]]、[[pom-build-gotchas]]
