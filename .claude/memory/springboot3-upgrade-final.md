---
name: springboot3-upgrade-final
description: Spring Boot 3 迁移总结（✅ 2026-08-19 已完成：5 应用全部启动成功）
metadata:
  type: project
---

# Spring Boot 3.x 迁移总结（已完成）

> ✅ 2026-08-19：全部 5 个应用在 **Windows + JDK 21** 下启动成功，登录链路验证通过。详细进度见 [[springboot3-upgrade-progress]]。

## ✅ 已完成

### 1. 依赖升级（pom.xml）
- Spring Boot 2.6.13 → **3.2.5**
- Spring Cloud 2021.0.4 → **2023.0.1**
- Spring Cloud Alibaba 2021.0.4.0 → **2023.0.3.2**
- Java 11 → **17**
- MyBatis-Plus 3.5.2 → **3.5.5**、Druid 1.2.16 → **1.2.21**、Flyway 7.10.0 → **9.22.3**
- 其他：commons-lang/io/codec、hutool、fastjson2、fastexcel、minio、okhttp、zxing 等

### 2. 命名空间迁移（javax → jakarta）
- 48 个 Java 文件：`servlet` / `validation` / `annotation` / `websocket` / `persistence`
- 3 个 fastjson2 配置：`support.spring.*` → `support.spring6.*`
- 保留的 `javax.*`（非 Jakarta 范畴）：crypto、imageio、sql、annotation.processing

### 3. Sentinel 依赖
- `sentinel-spring-webmvc-6x-adapter:1.8.6`（Boot 3 需要 6x 适配器）

### 4. 资源过滤调整
- `module-system/pom.xml`：nonFilteredFileExtensions 排除 pdfjs 二进制文件
- `learn/pom.xml`：新增 maven-resources-plugin 排除 `ttf/ttc/otf/dll` 等字体/二进制

### 5. 编译状态
- ✅ 全部 11 个模块编译/打包成功

### 6. 启动验证（全部通过）
- ✅ system-app（19093）：登录链路通（gateway → `/api/system/login` → JWT）
- ✅ gateway-app（9093）：`-Dspring.cloud.compatibility-verifier.enabled=false` 绕过误报
- ✅ socket-app（19095）、file-upload-app（19094）、ai-app（19096）

## 🔧 升级遗留的构建/启动问题修复

1. **learn mail 依赖**：`com.sun.mail:jakarta.mail:1.4.7` 构件不存在 → `com.sun.mail:javax.mail:1.6.2`（代码用 `javax.mail.*`）
2. **learn/design-pattern `-proc:none` 残留**：禁用注解处理导致 Lombok 不生效 → 移除双执行 hack
3. **system-app `factoryBeanObjectType` 报错**：根因 mybatis-spring 2.1.2 不兼容 Spring 6.1 → `module-base-core`、`module-file-upload` 换 `mybatis-plus-spring-boot3-starter`
4. **system-app DataSource 不生效**：`module-system` 换 `druid-spring-boot-3-starter`
5. **ai-app 无数据源启动失败**：排除 `DataSourceAutoConfiguration`、`MybatisPlusAutoConfiguration`

## 验证状态
全部 5 个应用启动成功，端口 19093 / 9093 / 19095 / 19094 / 19096。

## 关键文件
- [[springboot3-upgrade-progress]] — 进度记录（含启动命令、修复细节）
- [[pom-build-gotchas]] — POM 构建坑修复
- [[backend-startup]]、[[remote-nacos-hw]]、[[build-requires-jdk17]]
