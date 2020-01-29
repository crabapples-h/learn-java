package Mr.He.spring.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author Mr.He
 * 1/29/20 3:25 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@SpringBootTest
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    public void case1(){
        redisTemplate.opsForValue().set("crabapples","hello,world",10, TimeUnit.MINUTES);

    }
}
