package cn.crabapples.spring.common.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * TODO redisTemplate配置
 *
 * @author Mr.He
 * 2020/1/30 22:31
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Configuration
public class RedisTemplateConfigure {
    @Bean
    public RedisTemplate<String,Object> redisTemplate (RedisConnectionFactory redisConnectionFactory,FastJsonConfig fastJsonConfig){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate();
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        fastJsonRedisSerializer.setFastJsonConfig(fastJsonConfig);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setKeySerializer(fastJsonRedisSerializer);
//        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
//        redisTemplate.setStringSerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        return redisTemplate;
    }
}
