package cn.crabapples.common;

import cn.crabapples.common.utils.ReflectUtils;
import cn.crabapples.test.entity.SysUser;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Mr.He
 * 2023/8/30 20:31
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Aspect
@Component
@Slf4j
public class DictAspect {
    private static final Logger logger = LoggerFactory.getLogger(DictAspect.class);
    public final StringRedisTemplate redisTemplate;

    private static final String JAVA_UTIL_DATE = "java.util.Date";
    private static final String CONTROLLER_AOP = "execution(* cn.crabapples.*.controller.*.*(..))";
    Map<String, Map<String, String>> dict = new HashMap<>();

    public DictAspect(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 定义切点Pointcut
     */
    @Pointcut("execution(public * cn.crabapples.*.controller.*.*(..)) || @annotation(cn.crabapples.common.Dict)")
    public void dictService() {
    }

    @Around("dictService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Map<String, String> test = new HashMap<>();
        test.put("0", "测试0");
        test.put("1", "测试1");
        Map<String, String> delFlag = new HashMap<>();
        delFlag.put("0", "删除0");
        delFlag.put("1", "删除1");
        dict.put("test", test);
        dict.put("delFlag", delFlag);
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

    private Object parseDictText(Object result) {
        if (result instanceof ResponseDTO) {
            Object data = ((ResponseDTO) result).getData();
            Class clazz = data.getClass();
            Boolean hasDict = checkHasDict(clazz);
            if (!hasDict) {
                return result;
            }
            log.debug(" __ 进入字典翻译切面 DictAspect —— ");
            String json = JSONObject.toJSONString(data);
            JSONObject resultJson = JSONObject.parseObject(json, Feature.OrderedField);
            for (Field field : ReflectUtils.getAllFields(clazz)) {
                if (field.getAnnotation(Dict.class) != null) {
                    String code = field.getAnnotation(Dict.class).dictCode();
                    String text = field.getAnnotation(Dict.class).dictText();
                    String table = field.getAnnotation(Dict.class).dictTable();
                    String dictCode = code;
                    if (!StringUtils.isEmpty(table)) {
                        dictCode = String.format("%s,%s,%s", table, text, code);
                    }
                    String dictValue = resultJson.getString(field.getName());
                    String dictText = dict.get(dictCode).get(dictValue);
                    resultJson.put(field.getName() + "_dictText", dictText);
                }
            }
            ((ResponseDTO) result).setData(resultJson);
            return result;
        }
        return result;
    }

    /**
     * 检测返回结果集中是否包含Dict注解
     *
     * @param clazz 需要检查的class对象
     * @return 是否包含@Dict注解
     */
    private Boolean checkHasDict(Class clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (Objects.nonNull(field.getAnnotation(Dict.class))) {
                return true;

            }
        }
        return false;
    }
}
