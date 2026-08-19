---
name: learning-spring-lifecycle
description: Spring Bean 生命周期与三级缓存解决循环依赖笔记
metadata:
  type: reference
---

来源：`learn/src/main/java/spring/README.md`。

**Bean 生命周期**：实例化 → 初始化 → 使用 → 销毁

**循环依赖三级缓存**（`DefaultSingletonBeanRegistry`）：
- 一级缓存 `singletonObjects`：存放 bean 成品对象
- 二级缓存 `earlySingletonObjects`：存放 bean 半成品对象
- 三级缓存 `singletonFactories`：存放 lambda（bean 的工厂方法/ObjectFactory）

**查找顺序**：一级 → 二级 → 三级缓存。
- 无 AOP/代理时二级缓存即可解决循环依赖；三级缓存用于确定最终赋值用原始对象还是代理对象

**创建流程**：getBean → doGetBean → createBean → doCreateBean → createBeanInstance → populateBean（填充属性）
