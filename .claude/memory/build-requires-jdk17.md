---
name: build-requires-jdk17
description: 构建需 JDK 17（项目默认 17，JDK 11 报"无效的目标发行版：17"）
metadata:
  type: project
---

项目根 pom 默认 `<java.version>17</java.version>`，整个 reactor 需用 JDK 17 构建。

- `modules/module-ai`（及其 ai-app）硬性要求 Java 17——用 JDK 11 构建会失败：`无效的目标发行版：17`
- `learn/` 和 `module-base-core/` pom 中覆写为 11，**这两个模块用 JDK 11 编译**，其余模块用 17（Spring Boot 3.2 最低要求）

本机 JDK 17 路径：`/Users/mshe/developer/java/jdk-17.0.17.jdk/Contents/Home`。
本机另有 JDK 21（`/Users/mshe/developer/java/jdk-21.jdk`，另一台电脑主用）。

构建命令（跳过测试编译，否则 file-upload-app 测试缺依赖编译失败）：
```bash
JAVA_HOME=/Users/mshe/developer/java/jdk-17.0.17.jdk/Contents/Home \
mvn clean install \
  -pl modules-application/system-app,modules-application/gateway-app,modules-application/ai-app,modules-application/file-upload-app,modules-application/socket-app \
  -am -Dmaven.test.skip=true
```

注意用 `-Dmaven.test.skip=true`（跳过测试编译）；仅 `-DskipTests` 不够（file-upload-app 测试引用 spring-boot-test 但 POM 未加依赖）。相关：[[pom-build-gotchas]]、[[springboot3-upgrade-progress]]
