package cn.crabapples.common.config.jwt;

import cn.crabapples.common.config.jwt.JwtInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JwtInterceptorConfigure implements WebMvcConfigurer {
    @Value("${filePath}")
    private String filePath;
    @Value("${virtualPath}")
    private String virtualPath;

    private final JwtInterceptor jwtInterceptor;

    public JwtInterceptorConfigure(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    /**
     * 虚拟路径配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(virtualPath + "/**").addResourceLocations("file:" + filePath + "/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
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
