package cn.crabapples.common.dic;

import cn.crabapples.common.base.BaseEntity;
import cn.crabapples.common.utils.ReflectUtils;
import cn.crabapples.system.entity.SysDictItem;
import cn.crabapples.system.service.SystemDictService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * TODO 字典翻译注解
 *
 * @author Mr.He
 * 2023/8/30 20:30
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Getter
@Slf4j
public enum DictEnum {
    PAGE(IPage.class),
    LIST(List.class),
    OBJECT(BaseEntity.class);

    private final Type classType;

    DictEnum(Type classType) {
        this.classType = classType;
    }

    public static DictEnum getInstance(Object data) {
        if (data instanceof IPage) {
            return PAGE;
        }
        if (data instanceof List) {
            return LIST;
        }
        if (data instanceof BaseEntity) {
            return OBJECT;
        }
        return null;
    }

    /**
     * @param redisTemplate redis缓存
     * @param dictService   字典查询类
     * @param data          responseDTO中的data数据
     * @return 填充后的数据
     */
    public Object fillDictText(RedisTemplate<String, Map<String, String>> redisTemplate,
                               SystemDictService dictService, Object data) {
        List<?> dataList = null;
        Class<?> clazz = Object.class;
        switch (this) {
            case PAGE: {
                IPage<?> page = (IPage<?>) data;
                dataList = page.getRecords();
                break;
            }
            case LIST: {
                dataList = (List<?>) data;
                break;
            }
            case OBJECT: {
                clazz = data.getClass();
                break;
            }
        }
        // 判断是否是集合类型
        if (null != dataList && dataList.size() > 0) {
            clazz = dataList.get(0).getClass();
        }
        // 如果是集合类型，则遍历集合并填充数据
        if (null != dataList) {
            List resultList = new ArrayList<>();
            for (Object item : dataList) {
                resultList.add(fillDictText(redisTemplate, dictService, clazz, item));
            }
            // 如果是分页数据则将翻译填充后的数据放回原分页对象中
            if (this == PAGE) {
                IPage<?> page = (IPage<?>) data;
                page.setRecords(resultList);
                return page;
            }
            return resultList;
        }
        // 如果不是集合类型，翻译后直接返回
        return fillDictText(redisTemplate, dictService, clazz, data);
    }

    /**
     * @param redisTemplate redis缓存
     * @param dictService   字典查询
     * @param clazz         需要填充的对象class
     * @param item          需要填充的对象实例
     * @return 填充后的数据
     */
    private Object fillDictText(RedisTemplate<String, Map<String, String>> redisTemplate,
                                SystemDictService dictService, Class<?> clazz,
                                Object item) {
        for (Field field : ReflectUtils.getAllFields(clazz)) {
            try {
                if (field.getAnnotation(Dict.class) != null) {
                    String code = field.getAnnotation(Dict.class).dictCode();
                    String text = field.getAnnotation(Dict.class).dictText();
                    String table = field.getAnnotation(Dict.class).dictTable();
                    String dictCode = code;
                    if (!StringUtils.isEmpty(table)) {
                        dictCode = String.format("%s,%s,%s", table, text, code);
                    }
                    String fieldName = field.getName();
                    log.info("开始翻译:[{}]", fieldName);
                    field.setAccessible(true);
                    JSONObject itemJson = JSONObject.parseObject(JSONObject.toJSONString(item));
                    Object dictItemCode = field.get(item);
                    String dictText = getDictValue(redisTemplate, dictService, dictCode, String.valueOf(dictItemCode));
                    itemJson.put(fieldName + "_dictValue", dictText);
                    log.info("开始翻译:[{}]完成:[{}]", fieldName, dictText);
                    return itemJson;
                }
            } catch (IllegalAccessException e) {
                log.error("翻译:[{}]时出现异常:[{}]", field.getName(), e.getMessage());
            }
        }
        return item;
    }

    /**
     * @param redisTemplate redis缓存
     * @param dictService   字典查询
     * @param dictCode      字典编码
     * @param dictItemCode  字典项代码
     * @return 字典值
     */
    private String getDictValue(RedisTemplate<String, Map<String, String>> redisTemplate, SystemDictService dictService,
                                String dictCode, String dictItemCode) {
        Map<String, String> dictItemMap = redisTemplate.opsForValue().get(dictCode);
        if (Objects.isNull(dictItemMap)) {
            List<SysDictItem> dictValueList = dictService.getDictItemListByCode(dictCode);
            dictItemMap = new HashMap<>();
            for (SysDictItem item : dictValueList) {
                dictItemMap.put(item.getCode(), item.getValue());
            }
        }
        redisTemplate.opsForValue().set(dictCode, dictItemMap, 10, TimeUnit.MINUTES);
        return dictItemMap.get(dictItemCode);
    }
}
