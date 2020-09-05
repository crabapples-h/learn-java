package cn.crabapples.common.utils.jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * TODO 注册jwt全局拦截器
 *
 * @author Mr.He
 * 9/5/20 2:53 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Configuration
public class JwtInterceptorConfigure implements WebMvcConfigurer {
    private final JwtInterceptor jwtInterceptor;

    public JwtInterceptorConfigure(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    /**
     * 拦截器配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
