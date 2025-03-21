# 20190704

# 这是一个用于记录学习的项目
* 20230711
  * 多线程死锁演示
* 20230528
  * 多线程线程同步代码块演示
* 20230526
  * 多线程演示
* 20230316
  * 恢复被误删的commit记录
* 20230225
  * 增加策略模式演示 `pattern23.strategy.*`
  * 增加模板方法模式演示 `pattern23.template.*`
  * 增加观察者模式演示 `pattern23.observer.*`
* 20230225
  * 增加装饰器模式演示 `pattern23.decorator.*`
* 20230225
  * 修复建造者模式代码错误
  * 增加享元模式演示 `pattern23.flyweight.*`
  * 增加外观模式演示 `pattern23.facade.*`
  * 增加适配器模式演示 `pattern23.adapter_obj.*`
  * 增加切面日志工具AOPLog
* 20230219
  * 加入 jpa example查询演示
* 20230120
  * 加入 rabbitMq 演示
* 20221125
  * 整合模块，在dao中加入jpa和mybatis相关功能
  * 移除C51演示文件
* 20220516
  * 加入C51演示文件
* 20220506
  * 整理代码，移除重复代码
* 20220330
  * readme按时间倒序
  * 加入mybatis逆向工程依赖
  * 加入文件上传专用接口
  * 加入mpvue模板
* 20220313
  * 增加大量pdf文档
  * 增加多表查询sql
* 20220312
  * 增加sql函数学习文件
  * 增加sql约束学习文件
  * 增加文档模块
* 20220311
  * 增加sql学习文件
  * 移除chrome自动化控制
  * 提出子pom文件版本号至父pom文件
* 20220119
  * 增加nio演示
* 20220119
  * 增加bio演示
  * bio演示提出线程池
  * 修改前端模块名称
* 20211129
  * 加入GUI演示
    * AWT
      * 基础窗口演示
      * 按钮和输入框演示
      * 滚动条演示
      * FlowLayout布局演示
      * BorderLayout布局演示
      * GridLayout布局演示
      * 计算器演示
      * CardLayout布局演示-事件监听
      * BoxLayout布局演示
      * Box组件演示
      * 基本组件综合应用
      * dialog组件演示
      * fileDialog组件演示
      * 事件机制
      * Menu组件演示
      * PopupMenu组件演示
* 20211124
  * 加入socket、io流演示
  * 采用线程池处理socket连接
* 20211009
  * 增加react版前端模块
* 20210812
  * 修复动态菜单，动态路由bug
* 20210804
  * 增加vue3版前端模块
* 20210628
  * 修复rabbitMq消息投递不到指定队列问题`RabbitMqConfigure`
  * 增加raabbitmq队列监听handler`NoticeQueueHandler`
  * 增加内置redis功能`RedisServerConfigure`
  * 增加sse接口
  * 修复websocket接口
  * 部分配置文件添加注释
  * 缓存工具优化
  * 图片工具更名为二维码工具
* 20210602
  * 修复嵌套路由无法生成路由表问题
  * 修复v-auth在没有获取到数据时报错问题
  * 修复日志名称错误
  * 修复锁定用户Api地址错误
  * 删除菜单类中无用字段
* 20210518
  * 增加读取超大文本文件演示`ReadMaxFileDemo.java`
  * 增加多线程断点续传下载演示`FileDownloadDemo.java`
* 20210424
  * 增加分页查询方法
  * 修复编辑用户后密码被清空bug
  * 分离代码
    * 将`SysController`中方法分离至`SysRolesController`、`SysMenusController`、`SysUsersController`
    * 将`SysService`中方法分离至`SysRolesService`、`SysMenusService`、`SysUsersService`
    * 将`SysServiceImpl`中方法分离至`SysRolesServiceImpl`、`SysMenusServiceImpl`、`SysUsersServiceImpl`
  * 完善各种注释
  * 增加添加用户时验证用户名是否被使用
  * 前端登录时使用`async`和`await`关键字使获取数据的方法同步
  * 前端Api接口提入单独js文件`src/api/Apis.js`
* 20210423
  * 动态生成路由表
    * 登录时获取路由数据存入sessionStorage
    * 登录时获取路由数据存入vuex
  * 动态生成菜单
    * 登录时获取菜单数据存入sessionStorage
    * 登录时获取菜单数据存入vuex
  * 登录时获取token存入sessionStorage
  * 登录时获取token存入vuex
  * 登录时获取用户信息存入sessionStorage
  * 登录时获取用户信息存入vuex
  * 动态控制菜单
    * 登录时获取授权信息存入sessionStorage
    * 登录时获取授权信息数据存入vuex
  * 增加vue自定义指令`v-auth`用与鉴权用户是否有对应功能操作权限
* 20210413
  * jsr验证组合并入同一个文件
  * 修改ResponseDTO构造方法
  * 增加less支持
  * 引入antd自定义样式表
  * 修改模块名
    * boot-jpa-shiro -> boot-jpa-jwt
* 20210413
  * 增加系统角色模块
  * 角色和菜单关联
  * 用户和角色关联
  * 前端增加两个自定义组件
    * 带确认信息的按钮
    * 自定义颜色的按钮
  * 增加404跳转
  * 增加首页标题自定义
  * 加入vuex模块
  * 增加用户信息缓存
  * 修改token缓存机制
    * 先存入sessionStorage，然后从sessionStorage读入vuex的store中
  * 增加登录页面判断是否登录过逻辑
  * 后端api接口地址大量修改
  * 增加BaseRepository类
    * 加入一些基本查询方法
* 20210412
  * 增加字典信息
  * 加入entity2DTO方法
  * 重写dao相关方法
    * 增加基础dao并加入基础方法
    * 判断查询结果是否为空，如果为空则直接抛出ApplicationException
  * 移除shiro
  * 引入elasticsearch
  * 增加分页查询功能
  * 系统菜单树后端生成
* 20210325
  * 前端请求参数加密
* 20200905
  * 加入多数据源配置(根据包名)
  * 加入多数据源配置(使用AOP动态切换)
    * 使用spring的配置文件扫描功能,从classpath:datasource.properties中读取数据源信息
* 20200829
  * 加入mongodb
* 20200825
  * 加入jwt
    * 拦截所有url(可以使用`@JwtIgnore`来标记不需要拦截的url)
* 20200817
  * 加入前端模块
    * 前端的axiosUtils中默认定义登录地址为 `/api/loginCheck`
    * 前端的axiosUtils中默认定义退出地址为 `/api/logout`
* 20200715
  * 加入原型(克隆)模式 `pattern23.prototype.*`
* 20200713
  * 加入建造者模式 `pattern23.builder.*`
  * 使用maven聚合整理项目，移除冗余代码
* 20200608
  * 加入自定义异常拦截器注解`@ControllerAdvice`
  * 加入断言工具类 `AssertUtils`
* 20200606
  * 整理代码，提出重复代码
* 20200401
  * 加入前后端分离前端模块
* 20200323
  * 加入webSocket演示
* 20200321
  * 加入邮件发送工具类
  * 加入rabbitMq轮询分发演示
* 20200320
  * 加入rabbitMq简单队列演示
* 20200318
  * 临时关闭nacos配置中心
* 20200309
  * 加入shiro授权测试
  * 集成shiro授权,登陆成功后返回所有菜单，根据thymeleaf-shiro动态显示菜单(springBoot-jpa-shiro)
* 20200307
  * 登陆成功根据用户返回已授权菜单列表(springBoot-jpa-noShiro)
* 20200306
  * 加入springShiro认证登陆
* 20200305
  * 新增分支 springBoot-jpa-security
  * 新增分支 springBoot-jpa-shiro
  * 新增分支 springBoot-jpa-noShiro
  * 新增分支 springCloud-jpa-security
* 20200304
  * 修改BaseController,统一异常处理返回json格式数据`BaseController.java`
  * 前端模板加入form序列化工具`src/main/resources/static/js/common.js`
  * 前端模板加入thymeleaf公共代码`src/main/resources/templates/common.html`
  * 登陆返回数据修改为json格式`src/main/resources/templates/common.html`
  * 加密文件的方法加入日志`AesFileDemo.java`
* 20200303
  * 修改springCache配置,缓存至redis乱码问题`RedisTemplateConfigure.java`
* 20200302
  * 修复aesKey在linux下使用相同种子生成密钥不一致问题
    * Linux下默认的算法是“NativePRNG”
    * windows下默认是“SHA1PRNG”（sun提供的算法)
* 20200225
  * 引入vue.js前端index页面菜单使用vue生成
  * 引入nacos作为配置中心
* 20200224
  * 修改springBoot启动logo
  * 加入swagger注解属性字段相关注释`cn.crabapples.spring.test.form.*`
* 20200224
  * 前端页面加入登陆功能
* 20200130
  * service加入切面拦截 `AopConfigure.java`
* 20200129
  * 用户操作相关逻辑实现
* 20200127
  * 加入jpa测试crud
* 20200117
  * 加入swagger测试 `Swagger2Configure.java`
* 20191227
  * 加入抽象工厂模式测试 `pattern23.factoey.abstractFactory.*`
* 20191224
  * 加入简单工厂模式测试 `pattern23.factory.simplefactory.*`
  * 加入工厂方法模式测试 `pattern23.factoey.factorymethod.*`
* 20191220
  * 加入单例模式相关测试 `pattern23.singleton.*`
* 20191213
  * 加入Google令牌工具 `GoogleAuthenticatorSimpleUtils.java`
* 20191114
  * 修改日志类名
* 20190921
  * 加入异常类 `ApplicationException.java`
* 20190920
  * 加入jsr303校验演示
* 20190815
  * 加入AES加密文件演示 `AesFileDemo.java`
* 20190704
    * 加入springboot演示代码
    * 加入AES加密字符串演示 `AesStringDemo.java`
    * 加入RSA加密字符串演示 `RsaDemo.java`
