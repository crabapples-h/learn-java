package cn.crabapples.common.utils.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * TODO 缓存工具类
 *
 * @author Mr.He
 * 2021/2/4 17:21
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@Component
@Slf4j
public class CacheUtils {
    private final CacheConfigure cacheConfigure;
    private final RedisTemplate<String, Object> redisTemplate;

    public CacheUtils(RedisTemplate<String, Object> redisTemplate, CacheConfigure cacheConfigure) {
        this.redisTemplate = redisTemplate;
        this.cacheConfigure = cacheConfigure;
    }



    /**
     * 设置缓存
     *
     * @param key   key
     * @param value value
     * @param time  缓存时间(秒)
     */
    public void set(String key, Object value, long time) {
        log.debug("需要缓存的数据为-> key:[{}],value:[{}],时间为:[{}]秒", key, value, time);
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        log.info("数据-> key:[{}],时间:[{}]秒,已加入缓存", key, value);
    }

    /**
     * 设置缓存
     *
     * @param key   key
     * @param value value
     */
    public void set(String key, Object value) {
        log.debug("需要缓存的数据为-> key:[{}],value:[{}]", key, value);
        redisTemplate.opsForValue().set(key, value, cacheConfigure.getTime(), TimeUnit.SECONDS);
        log.info("数据-> key:[{}],时间:[{}]秒,已加入缓存", key, value);
    }

    /**
     * 获取数据
     *
     * @param key key
     * @return 缓存数据
     */
    public Object get(String key) {
        log.debug("需要获取的key为:[{}],", key);
        Object value = redisTemplate.opsForValue().get(key);
        log.debug("数据获取成功: key:[{}],value:[{}]", key, value);
        return value;
    }

    /**
     * 删除缓存数据
     *
     * @param key key
     * @return 是否删除成功
     */
    public Boolean remove(String key) {
        log.debug("需要删除的key为:[{}],", key);
        Boolean status = redisTemplate.delete(key);
        log.debug("数据删除成功: key:[{}],value:[{}]", key, status);
        return status;
    }
}
