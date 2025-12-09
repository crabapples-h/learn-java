# Server 模块 Bug 分析报告

> **生成时间**: 2025-12-05
> **分析范围**: server 目录下所有代码
> **风险等级**: 🔴 高风险 | 🟡 中风险 | 🟢 低风险

## 📋 项目概览

- **项目名称**: learn-java server 模块
- **技术栈**: Spring Boot 2.6.13 + MyBatis-Plus + Spring Cloud Alibaba
- **主要功能**: 用户管理、权限控制、多租户支持、动态数据源

---

## 🔴 高风险问题

### 1. JWT 密钥硬编码漏洞
**位置**: `src/main/resources/application-custom.properties:3`
**问题**: JWT密钥直接写在配置文件中
```properties
crabapples.jwt.base64Secret=MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=
```
**影响**:
- 密钥泄露后可伪造任意用户token
- 所有用户认证形同虚设

**修复建议**:
```properties
# 使用环境变量
crabapples.jwt.base64Secret=${JWT_SECRET:}
```
```java
// 或使用密钥管理服务
@Value("${crabapples.jwt.base64Secret}")
private String jwtSecret;
```

### 2. 密码明文日志泄露
**位置**: `src/main/java/cn/crabapples/system/system/service/impl/SystemServiceImpl.java:85`
**问题**: 用户密码被记录到日志
```java
log.info("开始登录->用户名:[{}],密码:[{}]", username, password);
```
**影响**:
- 日志文件泄露导致密码泄露
- 违反数据保护法规

**修复建议**:
```java
log.info("开始登录->用户名:[{}],密码长度:[{}]", username,
    password != null ? password.length() : 0);
```

### 3. 使用不安全的密码哈希算法
**位置**: `src/main/java/cn/crabapples/system/sysUser/service/impl/SystemUserServiceImpl.java`
**问题**: 使用MD5哈希密码
```java
return isCrypt ? MD5.create().digestHex(password.getBytes(StandardCharsets.UTF_8)) : password;
```
**影响**:
- MD5容易被彩虹表攻击
- 相同密码产生相同哈希值

**修复建议**:
```java
// 使用BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

public String encodePassword(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
}
```

---

## 🟡 中风险问题

### 4. JWT认证绕过风险
**位置**: `module-base-core/src/main/java/cn/crabapples/common/jwt/JwtInterceptor.java:56-58`
**问题**: 只验证token格式，未验证用户状态
```java
String userId = jwtTokenUtils.getUserId(token);
log.debug("token所属用户:[{}]", userId);
return true;
```
**影响**:
- 已注销用户的token仍可使用
- 被锁定用户可继续访问系统

**修复建议**:
```java
String userId = jwtTokenUtils.getUserId(token);
// 验证用户是否有效
SysUser user = userService.getById(userId);
if (user == null || user.getStatus() != 1) {
    throw new ApplicationException("用户无效或已被锁定");
}
return true;
```

### 5. 登录接口DoS攻击风险
**位置**: `src/main/java/cn/crabapples/system/system/controller/SystemController.java:82`
**问题**: 固定延时可能被利用
```java
Thread.sleep(500);
```
**影响**:
- 恶意请求可耗尽服务器资源
- 影响正常用户登录

**修复建议**:
```java
// 使用Redis实现登录限制
String key = "login:limit:" + username;
Long count = redisTemplate.opsForValue().increment(key);
if (count == 1) {
    redisTemplate.expire(key, 1, TimeUnit.HOURS);
}
if (count > 5) {
    throw new ApplicationException("登录尝试次数过多，请1小时后再试");
}
```

### 6. 缺少细粒度权限控制
**位置**: 整个controller层
**问题**: 接口缺少权限验证注解
**影响**:
- 用户可访问未授权功能
- 无法实现最小权限原则

**修复建议**:
```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN') or hasPermission('user', 'list')")
    public List<User> listUsers() {
        // ...
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasPermission('user', 'delete')")
    public void deleteUser(@PathVariable String id) {
        // ...
    }
}
```

---

## 🟢 低风险问题

### 7. 敏感错误信息泄露
**位置**: 多处异常处理
**问题**: 返回详细错误信息
```java
throw new ApplicationException("用户名不存在");
```
**影响**:
- 攻击者可利用错误信息枚举用户
- 暴露系统内部结构

**修复建议**:
```java
// 对外统一返回
throw new ApplicationException("用户名或密码错误");

// 日志记录详细信息
log.warn("登录失败 - 用户名不存在: {}", username);
```

### 8. 默认弱密码
**位置**: `src/main/resources/application-dev.yml:67-68`
**问题**: Druid监控使用弱密码
```yaml
datasource.druid.stat-view-servlet.login-username: admin
datasource.druid.stat-view-servlet.login-password: admin
```
**修复建议**:
```yaml
datasource.druid.stat-view-servlet.login-username: ${DRUID_USERNAME:admin}
datasource.druid.stat-view-servlet.login-password: ${DRUID_PASSWORD:}
```

---

## ⚡ 性能问题

### 9. 潜在N+1查询问题
**位置**: `src/main/java/cn/crabapples/system/system/service/impl/SystemServiceImpl.java:106-112`
**问题**: 多次查询获取关联数据
```java
List<SysRole> userRoleList = rolesService.getUserRoles();
List<String> roleIds = userRoleList.stream().map(SysRole::getId).collect(Collectors.toList());
List<SysMenu> roleMenuList = roleMenusService.getRoleMenusList(roleIds);
```
**修复建议**:
```java
// 使用MyBatis-Plus的JOIN查询
@Select("SELECT r.*, m.* FROM sys_role r " +
        "LEFT JOIN sys_role_menu rm ON r.id = rm.role_id " +
        "LEFT JOIN sys_menu m ON rm.menu_id = m.id " +
        "WHERE r.user_id = #{userId}")
List<RoleMenuDTO> getUserRoleMenus(@Param("userId") String userId);
```

---

## 🔧 代码质量改进建议

### 1. 移除调试代码
```java
// 需要移除
System.out.println("Debug info");
```

### 2. 统一异常处理
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> handleException(Exception e) {
        log.error("系统异常", e);
        return ResponseEntity.ok(Result.error("系统异常，请稍后重试"));
    }
}
```

### 3. 添加输入验证
```java
public class LoginForm {
    @NotBlank(message = "用户名不能为空")
    @Length(min = 3, max = 20, message = "用户名长度必须在3-20之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
             message = "密码必须包含大小写字母和数字，至少8位")
    private String password;
}
```

### 4. 实现安全配置
```java
@Configuration
public class SecurityConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("http://localhost:*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
```

---

## 📊 Bug 统计

| 风险等级 | 数量 | 占比 |
|---------|------|------|
| 🔴 高风险 | 3 | 33.3% |
| 🟡 中风险 | 3 | 33.3% |
| 🟢 低风险 | 2 | 22.2% |
| ⚡ 性能问题 | 1 | 11.2% |

**总计**: 9个问题

---

## 🎯 修复优先级建议

### 第一阶段（立即修复）
1. JWT密钥硬编码问题
2. 密码明文日志泄露
3. 不安全的密码哈希算法

### 第二阶段（1周内）
1. JWT认证绕过风险
2. 登录接口DoS攻击风险
3. 细粒度权限控制实现

### 第三阶段（1个月内）
1. 敏感错误信息泄露
2. 默认弱密码问题
3. N+1查询优化
4. 代码质量改进

---

## 📝 注意事项

1. **密码迁移**: 更换密码哈希算法时，需要考虑现有密码的迁移策略
2. **JWT轮换**: 实现JWT密钥轮换机制，避免服务中断
3. **性能测试**: 权限控制增强后需要进行性能测试
4. **安全审计**: 修复完成后建议进行第三方安全审计

---

## 📚 参考资源

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Spring Security最佳实践](https://spring.io/projects/spring-security)
- [JWT最佳实践](https://auth0.com/blog/json-web-token-best-practices/)
- [密码存储最佳实践](https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html)