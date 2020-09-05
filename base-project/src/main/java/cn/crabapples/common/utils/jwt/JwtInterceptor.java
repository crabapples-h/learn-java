package cn.crabapples.common.utils.jwt;

import cn.crabapples.common.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO 配置jwt拦截器(不拦截@JwtIgnore标记的url)
 *
 * @author Mr.He
 * 9/5/20 2:54 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Slf4j
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    private final JwtConfigure jwtConfigure;

    public JwtInterceptor(JwtConfigure jwtConfigure) {
        this.jwtConfigure = jwtConfigure;
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
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        final String authHeader = request.getHeader(jwtConfigure.getAuthKey());
        log.debug("授权Token:[{}]", authHeader);
        if (StringUtils.isBlank(authHeader)) {
            log.debug("token认证失败");
            throw new ApplicationException("登录信息异常", 401);
        }
        JwtTokenUtils.parseJWT(authHeader, jwtConfigure.getBase64Secret());
        return true;
    }

}
