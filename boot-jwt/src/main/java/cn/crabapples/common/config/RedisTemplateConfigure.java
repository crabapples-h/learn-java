package cn.crabapples.common.config;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.ResponseDTO;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

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
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 字符串Key序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // 对象值序列化
        FastJsonRedisSerializer<Object> objectRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        redisTemplate.setValueSerializer(objectRedisSerializer);
        redisTemplate.setHashValueSerializer(objectRedisSerializer);

        redisTemplate.setDefaultSerializer(objectRedisSerializer);
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, Map<String, String>> mapRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Map<String, String>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 字符串Key序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // 对象值序列化
        FastJsonRedisSerializer<Object> objectRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        redisTemplate.setValueSerializer(objectRedisSerializer);
        redisTemplate.setHashValueSerializer(objectRedisSerializer);

        redisTemplate.setDefaultSerializer(objectRedisSerializer);
        return redisTemplate;
    }

    @Component
    @ControllerAdvice
    public static class CustomExceptionHandler {
        private final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

        @ResponseBody
        @ExceptionHandler
        protected ResponseDTO applicationExceptionHandler(Exception e) {
            logger.warn("XHR出现异常:[{}]", e.getMessage(), e);
            if (e instanceof HttpMessageNotReadableException) {
                return ResponseDTO.returnError("参数错误");
            }
            if (e instanceof ApplicationException) {
                if (401 == ((ApplicationException) e).getCode()) {
                    return ResponseDTO.returnAuthFail("身份认证失败");
                }
            }
            return ResponseDTO.returnError("操作失败:" + e.getMessage());
        }
    }
}
