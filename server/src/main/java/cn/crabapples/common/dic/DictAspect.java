package cn.crabapples.common.dic;

import cn.crabapples.common.base.ResponseDTO;
import cn.crabapples.common.utils.ReflectUtils;
import cn.crabapples.system.sysDict.entity.SysDictItem;
import cn.crabapples.system.sysDict.service.impl.SystemDictServiceImpl;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.mybatisflex.core.paginate.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;
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
    public final RedisTemplate<String, Map<String, Map<String, String>>> redisTemplate;

    public final SystemDictServiceImpl dictService;

    private static final String JAVA_UTIL_DATE = "java.util.Date";
    private static final String CONTROLLER_AOP = "execution(* cn.crabapples.*.controller.*.*(..))";
    Map<String, Map<String, String>> dict = new HashMap<>();

    public DictAspect(RedisTemplate<String, Map<String, Map<String, String>>> redisTemplate, SystemDictServiceImpl dictService) {
        this.redisTemplate = redisTemplate;
        this.dictService = dictService;
    }

    /**
     * 定义切点Pointcut
     */
    @Pointcut("execution(public * cn.crabapples.*.*.controller.*.*(..)) || " +
            "@annotation(cn.crabapples.common.dic.EnableDict)")
//    @Pointcut("@annotation(cn.crabapples.common.dic.EnableDict)")
    public void dictService() {
    }

    @Around("dictService()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        IgnoreDict ignoreDict = methodSignature.getMethod().getDeclaredAnnotation(IgnoreDict.class);
        if (null != ignoreDict) {
            return point.proceed();
        }
//        EnableDict annotation = methodSignature.getMethod().getAnnotation(EnableDict.class);
//        System.err.println(annotation);
        long time1 = System.currentTimeMillis();
        Object result = point.proceed();
        long time2 = System.currentTimeMillis();
        log.info("获取JSON数据 耗时：" + (time2 - time1) + "ms");
        long start = System.currentTimeMillis();
        if (result instanceof ResponseDTO) {
            result = this.parseDictText(result);
        }
        long end = System.currentTimeMillis();
        log.info("注入字典到JSON数据  耗时" + (end - start) + "ms");
        return result;
    }

    // 翻译数据
    private Object parseDictText(Object result) {
        Class<?> clazz;
        Field[] allFields;
        try {

            if (result instanceof ResponseDTO) {
                Object data = ((ResponseDTO<?>) result).getData();
                if (data instanceof Page) {
                    List<?> records = ((Page<?>) data).getRecords();
                    if (!records.isEmpty()) {
                        log.debug("分页数据开始翻译:[{}]", records);
                        clazz = records.get(0).getClass();
                        allFields = ReflectUtils.getAllFields(clazz);
                        List dataList = fillDictText(records, allFields);
                        ((Page<?>) data).setRecords(dataList);
                    }
                } else if (data instanceof List) {
                    if (!((List<?>) data).isEmpty()) {
                        log.debug("列表数据开始翻译:[{}]", data);
                        clazz = ((List<?>) data).get(0).getClass();
                        allFields = ReflectUtils.getAllFields(clazz);
                        data = fillDictText((List<?>) data, allFields);
                    }
                } else {
                    log.debug("单个对象开始翻译:[{}]", data);
                    allFields = ReflectUtils.getAllFields(data.getClass());
                    data = fillDictText(data, allFields);
                }
                ((ResponseDTO<?>) result).setData(data);
            }
        } catch (Exception e) {
            log.warn("数据翻译失败,[{}],[{}]", e, result);
        }
        return result;
    }

    public Object fillDictText(Object data, Field[] fields) {
        if (fields.length == 0) {
            return data;
        }
        // fastjson不过滤null
        String jsonString = JSONObject.toJSONString(data, JSONWriter.Feature.WriteNulls);
        JSONObject itemJson = JSONObject.parseObject(jsonString);
        for (Field field : fields) {
            try {
                if (field.getAnnotation(Dict.class) != null) {
                    String dictTable = field.getAnnotation(Dict.class).dictTable();
                    String dictField = field.getAnnotation(Dict.class).dictField();
                    String dictCode = field.getAnnotation(Dict.class).dictCode();

                    String fullDictText = String.format("%s,%s,%s", dictTable, dictField, dictCode);
                    String fieldName = field.getName();
                    log.debug("开始翻译:[{}]", fieldName);
                    field.setAccessible(true);
                    Object value = field.get(data);
                    String dictText = getDictValue(fullDictText, String.valueOf(value));
                    itemJson.put(fieldName + "_dictText", dictText);
                    log.debug("开始翻译:[{}]完成:[{}]", fieldName, dictText);
                }
            } catch (IllegalAccessException e) {
                log.error("翻译:[{}]时出现异常:[{}]", field.getName(), e.getMessage());
            }
        }
        return itemJson;
    }

    /**
     * @param fullDictText 字典编码格式 [字典表名,字典字段名,字典编码]
     * @param value        需要翻译的值
     * @return 翻译后的值
     */
    private String getDictValue(String fullDictText, String value) {
        Map<String, Map<String, String>> dictItemMap = redisTemplate.opsForValue().get("DICT_MAP");
        dictItemMap = dictItemMap == null ? new HashMap<>() : dictItemMap;
        String[] split = fullDictText.split(",");
        String dictTable = split[0];
        String dictField = split[1];
        String dictCode = split[2];
        // 当翻译的数据为字典表数据时
        if (StringUtils.isEmpty(dictTable)) {
            List<SysDictItem> dictValueList = dictService.getDictItemListByCode(dictCode);
            Map<String, String> itemMap = new HashMap<>();
            for (SysDictItem item : dictValueList) {
                itemMap.put(item.getValue(), item.getText());
            }
            dictItemMap.put(fullDictText, itemMap);
        } else {
            // 当翻译的数据为其他表数据时

        }
        redisTemplate.opsForValue().set("DICT_MAP", dictItemMap, 10, TimeUnit.MINUTES);
        Map<String, String> itemMap = dictItemMap.get(fullDictText);
        itemMap = itemMap == null ? new HashMap<>() : itemMap;
        String text = itemMap.get(value);
        // 当翻译的数据不存在时，返回原值
        return null == text ? value : text;
    }

    public List<?> fillDictText(List<?> dataList, Field[] fields) {
        return dataList.stream().map(data -> fillDictText(data, fields))
                .collect(Collectors.toList());
    }
}
