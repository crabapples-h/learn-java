> bean的生命周期 `实例化` => `初始化` => `使用` => `销毁`
---
> 循环依赖

* 在`spring-beans/src/main/java/org/springframework/beans/factory/support/DefaultSingletonBeanRegistry.java`中有三个map
* 一级缓存 `Map<String,Object> singletonObjects = new ConcurrentHashMap<>(256)`
    * 存放bean的成品对象
* 二级缓存 `Map<String,Object> earlySingletObjects = new ConcurrentHashMap<>(16)`
    * 存放bean的半成品对象
* 三级缓存 `Map<String,ObjectFactory<?>> singletonFactories = new HashMap<>(16)`
    * 存放lambda表达式,即对应bean的工厂方法

---
> 查找顺序

1. 先从一级缓存获取成品对象
2. 如果一级缓存中没有，再从二级缓存获取半成品对象
3. 如果二级缓存中没有，再三级缓存获取对象工厂方法

* 当不存在Aop或者代理对象的时候，二级缓存可以解决循环依赖问题
* 使用三级缓存可以确定在最终赋值阶段是使用原始对象还是使用代理对象
*

---

1. 获取实例`getBean`
2. 实际获取实例`doGetBean`
3. 创建bean`createBean`
4. 实际创建bean`doCreateBean`
5. 创建bean实例`createBeanInstance`
6. 填充bean属性`populateBean`

---

* 实例化
* 初始化
* 使用
* 销毁
