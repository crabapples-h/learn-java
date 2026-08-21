---
name: springboot3-upgrade-progress
description: Spring Boot 3.x 升级进度（2026-08-19 ✅ 全部完成：5 应用在 Windows+JDK21 下启动成功）
metadata:
  type: project
---

# Spring Boot 3.x 升级 - 进度记录

## ✅ 阶段1-2 依赖升级与命名空间迁移（完成）

### 依赖版本（根 pom.xml）
| 组件 | 旧版本 | 新版本 |
|------|--------|--------|
| Spring Boot | 2.6.13 | **3.2.5** |
| Spring Cloud | 2021.0.4 | **2023.0.1** |
| Spring Cloud Alibaba | 2021.0.4.0 | **2023.0.3.2** |
| Java | 11 | **17** |
| MyBatis-Plus | 3.5.2 | **3.5.5** |
| Druid | 1.2.16 | 1.2.21（boot3 starter） |
| Flyway | 7.10.0 | 9.22.3 |
| commons-lang/io/codec、hutool、fastjson2、fastexcel、minio、okhttp、zxing | 升级 | 见 final 总结 |

### 命名空间迁移（48 个 Java 文件）
- `javax.servlet/validation/annotation/websocket/persistence.*` → `jakarta.*`
- fastjson2: `support.spring.*` → `support.spring6.*`（3 个配置文件）
- learn/pom.xml: `javax.mail` → **`com.sun.mail:javax.mail:1.6.2`**（代码用 javax.mail.*，注意不是 jakarta.mail）

## ✅ 阶段5 编译：全部通过（11 个模块）

## ✅ 启动验证：全部通过（2026-08-19，Windows + JDK 21）

| 应用 | 端口 | 状态 |
|------|------|------|
| system-app | 19093 | ✅ Started，登录链路通（gateway 9093 → /api/system/login → JWT） |
| gateway-app | 9093 | ✅ Started（需 `-Dspring.cloud.compatibility-verifier.enabled=false`） |
| socket-app | 19095 | ✅ Started |
| file-upload-app | 19094 | ✅ Started |
| ai-app | 19096 | ✅ Started（需排除 DataSource 自动配置） |

## 🔧 升级遗留的构建/启动问题修复（重要）

1. **`learn/pom.xml` mail 依赖无效**：`com.sun.mail:jakarta.mail:1.4.7` 构件不存在（升级改坐标漏改版本），代码仍用 `javax.mail.*` → 改为 `com.sun.mail:javax.mail` + 根 pom 属性改名 `javax.mail.version=1.6.2`
2. **`learn/`、`design-pattern/` 的 `-proc:none` 残留**：旧「移除lombok」提交留下，双执行 hack 导致 Lombok `@Slf4j` 不生成 `log` → 已移除 executions，恢复标准编译
3. **`learn/` 资源过滤损坏二进制字体**：`fonts/*.ttf/ttc/otf`、`intelbth.dll` → 添加 maven-resources-plugin nonFilteredFileExtensions
4. **system-app `factoryBeanObjectType` 报错根因 = mybatis-spring 2.1.2 不兼容 Spring 6.1**：`module-base-core`、`module-file-upload` 的 `mybatis-plus-boot-starter`（Boot2）→ **`mybatis-plus-spring-boot3-starter`**（mybatis-spring 3.0.3）；`module-socket` 的排除同步改名
5. **system-app DataSource 配置不生效**：`module-system` 用 Boot2 的 `druid-spring-boot-starter` → **`druid-spring-boot-3-starter`**（1.2.21）
6. **ai-app 无数据源却引入 DataSource 自动配置**：`application.properties` 排除 `DataSourceAutoConfiguration`、`MybatisPlusAutoConfiguration`

## 启动命令（Windows + JDK21，远程 Nacos）
```bash
java -Dspring.cloud.nacos.server-addr=hw.crabapples.cn:8848 \
     -Dspring.cloud.nacos.username=nacos "-Dspring.cloud.nacos.password=BestLoveBaby!" \
     [-Dspring.redis.host=127.0.0.1 "-Dspring.redis.password="] \
     [-Dspring.cloud.compatibility-verifier.enabled=false] \
     -jar modules-application/<app>/target/<app>-1.0-SNAPSHOT.jar
```
- gateway-app 必须加 `-Dspring.cloud.compatibility-verifier.enabled=false`（Boot 3.2.5 不在 2023.0.1 train 兼容范围，验证器误报）
- system-app 需 Redis（手动启动），并覆盖 bootstrap.yml 里写死的 `192.168.31.166:6379` / 密码 `123456789`
- Maven 本地仓库 = `D:\developer\apache-maven-3.9.16\repo`（非 ~/.m2）

## 相关记忆
- [[multi-machine-sync]] — 多机协作同步记录
- [[pom-build-gotchas]]、[[build-requires-jdk17]]、[[backend-startup]]、[[remote-nacos-hw]]
