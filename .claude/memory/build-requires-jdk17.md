---
name: build-requires-jdk17
description: 构建含 module-ai 时必须用 JDK 17，JDK 11 报"无效的目标发行版：17"
metadata:
  type: project
---

`modules/module-ai`（及其 ai-app）硬性要求 Java 17。用 JDK 11（默认 JAVA_HOME）构建 reactor 会失败：`无效的目标发行版：17`。

本机 JDK 17 路径：`/Users/mshe/developer/java/jdk-17.0.17.jdk/Contents/Home`。整个 reactor 用 JDK 17 构建即可（其他模块编译 8/11 均兼容）。

构建命令（跳过测试编译，否则 file-upload-app 测试缺依赖编译失败）：
```
JAVA_HOME=/Users/mshe/developer/java/jdk-17.0.17.jdk/Contents/Home \
mvn clean install -pl modules-application/system-app,modules-application/gateway-app,modules-application/ai-app,modules-application/file-upload-app,modules-application/socket-app -am -Dmaven.test.skip=true
```

注意用 `-Dmaven.test.skip=true`（跳过测试编译）；仅 `-DskipTests` 不够（file-upload-app 测试引用 spring-boot-test 但 POM 未加依赖）。相关：[[pom-build-gotchas]]
