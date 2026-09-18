---
name: camunda-app-setup
description: camunda-app/module-camunda（Camunda 7.24 + Boot 3.5.13）启动修复：ESCAPE_CHARACTER 版本混搭根因与后续隔离级别问题
metadata:
  type: project
---

# camunda-app 启动修复（2026-09-18）

模块：`modules/module-camunda`（库）+ `modules-application/camunda-app`（可启动应用，Nacos 服务名 `camunda`）。Camunda 7.24.0（camunda-bom），已加入 reactor（旧记忆「不在根 reactor」已过时）。

## 问题：NoSuchFieldError SystemPropertyUtils.ESCAPE_CHARACTER

- 报错：`Class org.springframework.util.SystemPropertyUtils does not have member field 'java.lang.Character ESCAPE_CHARACTER'`
- **根因（版本混搭）**：根 pom 的 parent 是 `spring-boot-starter-parent:3.5.13`，但根 pom `<dependencyManagement>` 里 import 的 `spring-boot-dependencies` 仍是 `${spring-boot.version}=3.2.5`。结果 camunda-app 类路径被两套管理各管一半：
  - spring-boot-*（spring-boot、autoconfigure、各 starter）→ **3.5.13**（parent 继承，import 的 BOM 压不住）
  - spring-framework（spring-core/beans/web/...）→ **6.1.6**、spring-data-redis 3.2.5（3.2.5 BOM 侧）
  - 3.5.13 的字节码引用 Spring 6.2 才有的 `SystemPropertyUtils.ESCAPE_CHARACTER`（`Character` 类型，6.1.x 无此字段；spring-beans/spring-web 6.2 均引用）→ NoSuchFieldError
- 排查手段：`mvn dependency:tree -pl modules-application/camunda-app -am` + `help:effective-pom` 看实际管理版本；`javap -p` 对比 spring-core 6.1.15（无字段）vs 6.2.17（有字段）

## 修复（3 个改动）

1. `camunda-app/pom.xml` 新增 `<dependencyManagement>` import `spring-boot-dependencies:3.5.13`（直接在 app POM import，才能把 framework 统一到 6.2.17；spring-boot-* 仍是 parent 的 3.5.13，正好一致）
2. `module-camunda/pom.xml`：spring-boot-dependencies BOM 和 spring-boot-maven-plugin 3.5.5 → **3.5.13**（与父/插件对齐）
3. `module-camunda/pom.xml` 库模块的 spring-boot-maven-plugin 加 `<configuration><skip>true</skip></configuration>`（无主类 repackage 报 Unable to find main class，见 [[pom-build-gotchas]]）

修复后：Boot 3.5.13 + Framework 6.2.17 + spring-data 3.5.10 全一致，打包通过，启动日志中 ESCAPE_CHARACTER 报错为 0。

## 启动注意（冒烟实测）

- 标准启动参数同其他应用（Nacos 凭据 + Redis 覆盖）：
  `-Dspring.cloud.nacos.server-addr=hw.crabapples.cn:8848 -Dspring.cloud.nacos.username=nacos -Dspring.cloud.nacos.password=BestLoveBaby! -Dspring.redis.host=127.0.0.1 -Dspring.redis.password=`
- Nacos 客户端默认写 `~/logs/nacos/*.log`；无写权限时 Boot 3.5 把 logback 错误升级为致命错误（`Logback configuration error detected`）。可 `-Dnacos.logs.path=<可写目录>` 改路径
- Nacos 配置 dataId=`camunda.yaml`（DEFAULT_GROUP）**当前为空**；两个模块里都没有本地 yml/properties，配置需补到 Nacos
**Spring Cloud 兼容校验器（2026-09-18 最终方案=升级，曾短暂关过开关）**：Boot 3.5.13 报「not compatible with this Spring Cloud release train [3.2.x,3.3.x]」。**不能降级 Boot（spring-ai 需要 3.5）**，同日完成根 pom 全栈升级：SC 2023.0.1→**2025.0.0**、SCA 2023.0.3.2→**2025.0.0.0**（适配 Boot 3.5.x，nacos-client 3.0.3/sentinel 1.8.9），校验器报错自然消失，bootstrap.yaml 里的 `compatibility-verifier.enabled=false` 已删除，camunda-app 里临时加的 boot-deps BOM import 也已删除
- **ENGINE-12019 隔离级别（已解决）**：bootstrap.yaml 的 dataBaseParam 末尾加 `&sessionVariables=transaction_isolation=''READ-COMMITTED''`（YAML 单引号串内单引号双写），每个连接强制 RC（实测 -Dcamunda.bpm.database.skip-isolation-level-check 不生效，starter 不暴露该属性）
- 配置全貌见 camunda-app `src/main/resources/bootstrap.yaml`（端口 19098，数据源 localhost learn root/root，camunda admin/admin）
- **2026-09-18 验证**：`Started CamundaApplication` 启动成功，Tomcat 19098；升级 SC 2025.0.0 后无开关原生启动同样成功（gateway-app 无开关 Netty 9093 启动成功），ESCAPE_CHARACTER/校验器/隔离级别三个错误均为 0

相关：[[backend-startup]]、[[remote-nacos-hw]]、[[pom-build-gotchas]]、[[project-overview]]
