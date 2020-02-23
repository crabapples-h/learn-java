package cn.crabapples.spring.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * TODO 静态资源配置
 *
 * @author Mr.He
 * 2/24/20 12:51 AM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    /**
     * 静态资源注册，不注册的话访问不了
     */
    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("*.css").addResourceLocations("classpath:/*/*.css");
        registry.addResourceHandler("*.js").addResourceLocations("classpath:/*/*.js");
//        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
        super.addResourceHandlers(registry);
    }

//    /**
//     * 拦截器注册，不用拦截器的话就可以把这个删了，要自己写拦截器就多注册一个
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new InitInterceptor()).addPathPatterns("/**");
//        super.addInterceptors(registry);
//    }

//    /**
//     * 异步乱码解决方式
//     * @return
//     */
//    @Bean
//    public HttpMessageConverter<String> responseBodyConverter() {
//        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
//        return converter;
//    }
//
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        super.configureMessageConverters(converters);
//        converters.add(responseBodyConverter());
//    }

}