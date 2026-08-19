---
name: multi-datasource
description: 多数据源（动态切换）配置方式
metadata:
  type: project
---

来源：`modules/module-system/src/main/java/cn/crabapples/common/datasource/dynamicaop/readme.md` 与 `datasource.properties`。

- 主启动类需 `@Import(DynamicDataSourceRegister)` 注入动态数据源注册器
- 使用 `@DataSourceChange(name="xxx")` 注解手动切换数据源
- 数据源信息从 `datasource.properties` 读取（`datasource.names=primary,second`，支持按包名多数据源 + AOP 动态切换）
- system-app 主启动类中 `DynamicDataSourceRegister` 相关 Import 目前被注释掉（未启用）
