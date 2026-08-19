# 项目记忆

## 项目概述

多模块 Maven 学习/演示项目（`cn.crabapples:learn-java`）。Java 11，Spring Boot 2.6.13，Spring Cloud 2021.0.4，Spring Cloud Alibaba 2021.0.4.0。包含独立学习代码、设计模式演示、Spring Cloud 微服务架构（module-base-core → modules → modules-application）、多个前端项目和插件。根目录 `AGENTS.md` 记录了模块布局和注意事项。

## 规则

- 本项目所有提示、对话、输出均使用中文
- AGENTS.md 使用中文
- 所有项目记忆必须保存在项目目录下（如 `.mimocode/MEMORY.md`），通过 git 管理，禁止保存在用户目录（`~/.local/share/mimocode/` 等）
- 每次会话开始时主动读取 `.mimocode/MEMORY.md`
- bug 修复流程：先写文档 → 保存记忆 → 执行修复 → 更新记忆

## 架构决策

- AGENTS.md 作为简洁的代理指令文件，聚焦于非显而易见的结构事实、模块边界和环境陷阱，优先提供高信号内容而非泛泛建议。

## 已发现的持久知识

- `module-base-core` 的父 POM 是 `modules`（而非 root）——非显而易见的模块层级
- `modules/` = 库代码；`modules-application/` = 可部署的 Spring Boot 应用——命名不同
- 前端 lockfile 被 gitignore——构建前必须 `npm install`
- `lib/` 目录打包进 `BOOT-INF/lib/`——本地 JAR 会被烘焙进 fat JAR
- 默认 Maven profile 是 `mac`（activeByDefault），nacos 地址 `127.0.0.1:8848`
- `modules-application` 默认跳过测试（surefire `skipTests=true`）——用 `-DskipTests=false` 才会执行
- `redis-server` 是独立模块，不在根 Maven reactor 中
- `camunda-server` 使用 Spring Boot 3.5.5，独立于主多模块构建
- `frontend-react` 模块在 `frontend/pom.xml` 中已注释掉
