---
name: pom-build-gotchas
description: 2026-08-19 修复的三类 POM 构建问题（relativePath、库模块 repackage、system-app skip）
metadata:
  type: project
---

- **module-base-core 父 POM**：需显式 `<relativePath>../modules/pom.xml</relativePath>`。它位于仓库根目录，默认 `../pom.xml` 指向 root，导致父链属性解析不到（如 `knife4j.version`）。
- **modules/ 库模块误配 spring-boot-maven-plugin**：module-ai/module-gateway/module-socket 无主类却配了 repackage → 构建失败 "Unable to find main class"，已加 `<configuration><skip>true</skip></configuration>`。module-system/module-file-upload 同样有该插件（能侥幸通过但主类名是错的）。
- **system-app 的 spring-boot-maven-plugin** 原本 `<skip>true</skip>` 导致只出 13KB thin jar、无法 `java -jar`，已移除。注意 `-Dspring-boot.repackage.skip=false` 命令行覆盖对 POM 内硬编码 skip 不生效。

构建需 JDK 17，见 [[build-requires-jdk17]]。
