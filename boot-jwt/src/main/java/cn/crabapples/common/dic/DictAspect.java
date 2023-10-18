package cn.crabapples.common.dic;

import cn.crabapples.common.ResponseDTO;
import cn.crabapples.system.service.impl.SystemDictServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author Mr.He
 * 2023/8/30 20:31
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
//@Aspect
//@Component
@Slf4j
public class DictAspect {
    private static final Logger logger = LoggerFactory.getLogger(DictAspect.class);
    public final RedisTemplate<String, Map<String, String>> redisTemplate;

    public final SystemDictServiceImpl dictService;

    private static final String JAVA_UTIL_DATE = "java.util.Date";
    private static final String CONTROLLER_AOP = "execution(* cn.crabapples.*.controller.*.*(..))";
    Map<String, Map<String, String>> dict = new HashMap<>();

    public DictAspect(RedisTemplate<String, Map<String, String>> redisTemplate, SystemDictServiceImpl dictService) {
        this.redisTemplate = redisTemplate;
        this.dictService = dictService;
    }

    /**
     * 定义切点Pointcut
     */
    @Pointcut("execution(public * cn.crabapples.*.controller.*.*(..)) || @annotation(cn.crabapples.common.dic.Dict)")
    public void dictService() {
    }

    @Around("dictService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long time1 = System.currentTimeMillis();
        Object result = pjp.proceed();
        long time2 = System.currentTimeMillis();
        log.debug("获取JSON数据 耗时：" + (time2 - time1) + "ms");
        long start = System.currentTimeMillis();
        result = this.parseDictText(result);
        long end = System.currentTimeMillis();
        log.debug("注入字典到JSON数据  耗时" + (end - start) + "ms");
        return result;
    }

    // 翻译数据，实现过程在DictEnum枚举中
    private Object parseDictText(Object result) {
        if (result instanceof ResponseDTO) {
            Object data = ((ResponseDTO) result).getData();
            DictEnum instance = DictEnum.getInstance(data);
            Object resultData = instance.fillDictText(redisTemplate, dictService, data);
            ((ResponseDTO) result).setData(resultData);
        }
        return result;
    }
}
