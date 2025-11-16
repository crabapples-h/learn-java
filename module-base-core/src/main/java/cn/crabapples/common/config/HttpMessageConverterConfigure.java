package cn.crabapples.common.config;

import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;

/**
 * TODO springboot 消息转换配置(主要用于配置fastJson解析)
 *
 * @author Mr.He
 * 2020/1/31 2:02
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Configuration
public class HttpMessageConverterConfigure implements WebMvcConfigurer {

    @Bean
    public FastJsonHttpMessageConverter httpMessageConverters(FastJsonConfig fastJsonConfig) {
        /*
         * 因为配置的MessageConverters后会首选使用使用配置的消息转换器来处理消息
         * FastJsonHttpMessageConverter 处理的媒体类型是 *//*,但是没有对sse请求进行处理
         * 消息被FastJsonHttpMessageConverter处理后无法被其他处理器处理，就导致sse无法正常发送数据
         * 经测试，移除下方List<HttpMessageConverter<?>>中的第一个元素，即移除FastJsonHttpMessageConverter就可以正常发送数据
         * 或者配置FastJsonHttpMessageConverter的supportedMediaTypes，只支持application/json
         */
        FastJsonHttpMessageConverter messageConverter = new FastJsonHttpMessageConverter();
        messageConverter.setFastJsonConfig(fastJsonConfig);
        messageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        return messageConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.remove(0);
        // 添加对SSE的支持
//        converters.add(new ServerSentEventHttpMessageReader());
        // 添加对JSON的支持
//        converters.add(new MappingJackson2HttpMessageConverter());
//        converters.add(new FastJsonHttpMessageConverter());
    }
}
