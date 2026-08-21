package cn.crabapples.common.jwt;

import cn.crabapples.common.annotation.JwtIgnore;
import cn.crabapples.common.base.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * TODO 配置jwt拦截器(不拦截@JwtIgnore标记的url)
 *
 * 修复 P0-1：除校验 token 格式外，额外调用 UserAuthChecker
 * 校验用户状态（是否被锁定/删除），避免已失效的旧 token 继续访问。
 * 校验器由具体用户管理模块提供（接口反转，避免 module-base-core 反向依赖）。
 *
 * @author Mr.He
 * 9/5/20 2:54 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtTokenUtils jwtTokenUtils;

    /**
     * 用户态校验器（可选注入）。
     * - module-system 会提供实现（{@code sysUserAuthCheckerImpl}），校验 status=0 && delFlag=0
     * - socket-app / file-upload-app / ai-app 等不提供实现时为 null，仅校验 token 格式（向后兼容）
     */
    @Autowired(required = false)
    private UserAuthChecker userAuthChecker;

    public JwtInterceptor(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 忽略带JwtIgnore注解的请求, 不做后续token认证校验
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            JwtIgnore jwtIgnore = handlerMethod.getMethodAnnotation(JwtIgnore.class);
            if (jwtIgnore != null) {
                return true;
            }
        }
        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            response.setStatus(HttpStatus.OK.value());
            return true;
        }
        String token = request.getHeader(jwtTokenUtils.getAuthKey());
        log.debug("授权Token:[{}]", token);
        if (StringUtils.isBlank(token)) {
            log.warn("token认证失败");
            throw new ApplicationException("登录信息异常", 401);
        }
        String userId = jwtTokenUtils.getUserId(token);
        log.debug("token所属用户:[{}]", userId);

        // 校验用户态：若 classpath 提供 UserAuthChecker 实现，验证用户未被锁定/删除
        if (userAuthChecker != null && !userAuthChecker.isUserActive(userId)) {
            log.warn("用户态校验失败->userId:[{}] (已被锁定或删除)", userId);
            throw new ApplicationException("登录信息已失效，请重新登录", 401);
        }
        return true;
    }

}
