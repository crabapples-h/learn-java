package cn.crabapples.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfigure implements WebMvcConfigurer {
    //文件存储路径
    @Value("${filePath}")
    private String filePath;
    //文件访问路径
    @Value("${virtualPath}")
    private String virtualPath;

    /**
     * 虚拟路径配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(virtualPath + "/**").addResourceLocations("file:" + filePath + "/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
