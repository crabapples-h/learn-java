---
name: multi-machine-sync
description: 2026-08-20 多机协作同步 - 另一台电脑（Windows+JDK21）已完成 SB3 升级并推送，本机（Mac+JDK17）已拉取同步
metadata:
  type: project
---

# 多机协作同步记录

> 2026-08-20：另一台电脑（**Windows + JDK 21**）完成了 Spring Boot 3.x 升级的全部修复，已推送；本机（**Mac + JDK 17**）已拉取同步。

## 两台机器的环境差异

| 项目 | Mac（本机） | Windows（另一台） |
|------|-------------|-------------------|
| 操作系统 | macOS Darwin 25.1.0 | Windows |
| Maven 仓库 | `$MAVEN_HOME/repo` = `/Users/mshe/developer/apache-maven-3.9.4/repo` | `D:\developer\apache-maven-3.9.16\repo` |
| JDK 17 | `/Users/mshe/developer/java/jdk-17.0.17.jdk/Contents/Home` | 默认 JAVA_HOME |
| JDK 21 | `/Users/mshe/developer/java/jdk-21.jdk` | **主用** |
| Redis | 127.0.0.1:6379 无密码 | — |
| MySQL | 127.0.0.1:3306 root/root | — |

> **共同点**：远程 Nacos `hw.crabapples.cn:8848`（nacos / BestLoveBaby!）、gateway(9093) / system(19093) / file-upload(19094) / socket(19095) / ai(19096) 端口一致。

## 另一台电脑完成的关键修复（已合并到主仓库）

1. **`system-app` `factoryBeanObjectType` 根因 = mybatis-spring 2.1.2 不兼容 Spring 6.1**：
   - `module-base-core/pom.xml`、`module-file-upload/pom.xml`：`mybatis-plus-boot-starter` → **`mybatis-plus-spring-boot3-starter`**
   - 后者带 mybatis-spring **3.0.3**，与 Spring 6.1 兼容
   - `module-socket/pom.xml` 的 mybatis-plus exclusion 同步改名

2. **`system-app` DataSource 配置不生效**：
   - `module-system/pom.xml`：`druid-spring-boot-starter` → **`druid-spring-boot-3-starter`**（1.2.21）
   - 否则 Boot 3 回退到 HikariCP，读不到 `spring.datasource.druid.*`

3. **`learn/pom.xml` mail 依赖错误**：
   - 旧：`com.sun.mail:jakarta.mail:1.4.7`（构件不存在）
   - 新：`com.sun.mail:javax.mail:1.6.2`（代码 `MailDemo.java` 用 `javax.mail.*`，注意**不是** jakarta.mail）
   - 根 pom 属性 `javax.mail.version=1.6.2`

4. **`learn/`、`design-pattern/` 的 `-proc:none` 残留**：
   - 旧「移除 lombok」提交留下的双执行 hack
   - 禁用注解处理导致 Lombok `@Slf4j` 不生成 `log`
   - 已移除 executions，恢复标准编译

5. **`learn/` 资源过滤损坏二进制**：
   - `fonts/*.ttf/ttc/otf`、`intelbth.dll` 等被错误 UTF-8 解码
   - 添加 maven-resources-plugin nonFilteredFileExtensions

6. **`ai-app` 无数据源却引入 DataSource 自动配置**：
   - `application.properties` 排除 `DataSourceAutoConfiguration`、`MybatisPlusAutoConfiguration`
   - 原因：`module-ai` → `module-base-core`（含 mybatis-plus → spring-boot-starter-jdbc）

## 状态（截至 2026-08-20）

✅ **Spring Boot 3.2.5 升级 100% 完成**：
- 5 个应用全部启动成功（system/gateway/socket/file-upload/ai）
- 登录链路验证通过：gateway(9093) → `/api/system/login` → JWT
- 11 个模块全部编译通过

## 跨机协作原则

1. **记忆以 git 同步**：本项目 `.claude/memory/` 通过 git 跟踪，两台机器可共享记忆
2. **路径差异标记**：JDK / Maven 仓库等本地路径信息在记忆里**标注本机** vs **另一台**
3. **环境配置在应用启动时注入**：Nacos 凭据、Redis 覆盖、compatibility-verifier 开关都通过 `-D` 参数传递，不写死在代码
4. **关键修复写进代码 + 记忆**：构建坑、starter 换装、邮件坐标等都同步到 [[pom-build-gotchas]] 和 [[multi-machine-sync]]

## 本机（Mac）后续可执行

- 代码已同步，可直接 `mvn package` + 启动验证
- JDK 17 在 `/Users/mshe/developer/java/jdk-17.0.17.jdk/Contents/Home`
- 启动命令已标准化（见 [[multi-machine-sync]] 和 [[backend-startup]]）

相关：[[multi-machine-sync]]、[[pom-build-gotchas]]、[[backend-startup]]、[[remote-nacos-hw]]、[[local-infra]]、[[maven-local-repo]]
