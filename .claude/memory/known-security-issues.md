---
name: known-security-issues
description: 项目已知安全/质量问题（来自 frontend-vue2 与后端 Bug 分析报告，2025-12-05 生成）
metadata:
  type: project
---

来源：`frontend/frontend-vue2/前端代码分析报告.md` 与 `modules-application/system-app/Bug分析报告.md`（后者与 `modules/module-system/Bug分析报告.md` 内容相同）。均为分析建议，尚未全量修复。

**🔴 高风险（后端）**
- JWT 密钥硬编码：`application-custom.properties` 的 `crabapples.jwt.base64Secret`
- 密码明文日志：`SystemServiceImpl.java:85` `log.info("开始登录->用户名:[{}],密码:[{}]",...)`
- 不安全的 MD5 密码哈希：`SystemUserServiceImpl` 用 `MD5.create().digestHex(...)`

**🔴 高风险（前端）**
- RSA 公钥硬编码在 `frontend-vue2/src/settings.js:8-16`
- 生产环境输出大量调试信息（Login.vue、router/index.js、request.js）
- 无 CSRF 防护（request.js 仅带 crabapples-token 头）

**🟡 中风险**
- ~~JWT 认证只验 token 格式未验用户状态~~ → **已修复（2026-08-20，P0-1）**：JwtInterceptor 通过 UserAuthChecker 校验用户 status/delFlag，见 [[p0-1-jwt-interceptor-fix]]
- 登录接口 `Thread.sleep(500)` 固定延时可被 DoS
- 缺少细粒度权限注解（整个 controller 层）
- Token 存 localStorage 易受 XSS；依赖版本过旧（vue 2.6.14 等）；devServer host 0.0.0.0

**🟢 低风险/性能**
- 错误信息过度暴露（提示"用户名不存在"可枚举用户）；Druid 监控弱密码 admin/admin
- N+1 查询：`SystemServiceImpl.java:106-112` 多次查询获取角色/菜单
