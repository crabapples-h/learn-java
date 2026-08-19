---
name: pom-build-gotchas
description: POM 构建坑修复（relativePath、库模块 repackage、system-app skip、SB3 升级遗留问题）
metadata:
  type: project
---

## 第一类：父 POM 与打包
- **module-base-core 父 POM**：需显式 `<relativePath>../modules/pom.xml</relativePath>`。它位于仓库根目录，默认 `../pom.xml` 指向 root，导致父链属性解析不到（如 `knife4j.version`）。
- **modules/ 库模块误配 spring-boot-maven-plugin**：module-ai/module-gateway/module-socket 无主类却配了 repackage → 构建失败 "Unable to find main class"，已加 `<configuration><skip>true</skip></configuration>`。module-system/module-file-upload 同样有该插件（能侥幸通过但主类名是错的）。
- **system-app 的 spring-boot-maven-plugin** 原本 `<skip>true</skip>` 导致只出 13KB thin jar、无法 `java -jar`，已移除。注意 `-Dspring-boot.repackage.skip=false` 命令行覆盖对 POM 内硬编码 skip 不生效。

## 第二类：Spring Boot 3 升级遗留（2026-08-19 修复，Windows 上发现）
- **`learn/pom.xml` mail 坐标无效**：`com.sun.mail:jakarta.mail:1.4.7` 构件不存在（aliyun 无此版本），且代码 `MailDemo.java` 用 `javax.mail.*` → 改为 `com.sun.mail:javax.mail`，根 pom 属性 `javax.mail.version=1.6.2`
- **`learn/`、`design-pattern/` 的 `-proc:none` 残留**（旧「移除lombok」提交遗留）：双执行 hack 让注解处理关闭，Lombok `@Slf4j` 不生成 `log` → 已移除 executions，恢复标准编译。注意此模式在模块重新引入 lombok 后必须清理
- **Boot2 starter 未换成 Boot3**：
  - `mybatis-plus-boot-starter` → **`mybatis-plus-spring-boot3-starter`**（module-base-core、module-file-upload）；前者带 mybatis-spring **2.1.2**，与 Spring 6.1 不兼容 → system-app `factoryBeanObjectType` 报错根因；后者带 mybatis-spring 3.0.3
  - `druid-spring-boot-starter` → **`druid-spring-boot-3-starter`**（module-system）；否则 Boot3 回退 Hikari、读不到 `spring.datasource.druid.*`
  - `module-socket` 对 mybatis-plus 的 exclusion 需同步改名
- **`learn/` 资源过滤损坏二进制**：`fonts/*.ttf/ttc/otf`、`intelbth.dll` → 加 maven-resources-plugin nonFilteredFileExtensions（与 modules-application 一致）

构建需 JDK 17，见 [[build-requires-jdk17]]。升级启动相关见 [[springboot3-upgrade-progress]]。
