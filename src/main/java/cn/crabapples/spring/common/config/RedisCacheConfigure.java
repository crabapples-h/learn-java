package cn.crabapples.spring.common.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

/**
 * TODO springCache配置类
 *
 * @author Mr.He
 * 2020/1/29 10:12
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Configuration
//@CacheConfig
@EnableCaching
public class RedisCacheConfigure {
    private final RedisConnectionFactory redisConnectionFactory;

    public RedisCacheConfigure(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Bean
    public CacheManager cacheManager() {
        // 默认缓存一天 86400秒

        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(10L))
                .disableCachingNullValues();
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(defaultCacheConfig).build();


//        return RedisCacheManager.builder(RedisCacheWriter.lockingRedisCacheWriter
//                (redisConnectionFactory)).cacheDefaults(defaultCacheConfig).transactionAware().build();

    }

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        // 字符串Key序列化
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(stringRedisSerializer);
//        redisTemplate.setHashKeySerializer(stringRedisSerializer);
//        // 对象值序列化
//        FastJsonRedisSerializer<Object> objectRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
//        redisTemplate.setValueSerializer(objectRedisSerializer);
//        redisTemplate.setHashValueSerializer(objectRedisSerializer);
//        return redisTemplate;
//    }

}
