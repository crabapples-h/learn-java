package cn.crabapples.common;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
    public final StringRedisTemplate redisTemplate;

    private static final String JAVA_UTIL_DATE = "java.util.Date";

    public DictAspect(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 定义切点Pointcut
     */
    @Pointcut("execution(public * cn.crabapples.*.*Controller.*(..)) || @annotation(cn.crabapples.common.Dict)")
    public void excudeService() {
    }

    @Around("excudeService()")
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

    private Object parseDictText(Object result) {
        if (result instanceof ResponseDTO) {
            //step.1 筛选出加了 Dict 注解的字段列表
            List<Field> dictFieldList = new ArrayList<>();
            // 字典数据列表， key = 字典code，value=数据列表
            Map<String, List<String>> dataListMap = new HashMap<>(5);
            Object data = ((ResponseDTO) result).getData();
            Class clazz = data.getClass();
            //update-begin--Author:zyf -- Date:20220606 ----for：【VUEN-1230】 判断是否含有字典注解,没有注解返回-----
            Boolean hasDict = checkHasDict(clazz);
            if (!hasDict) {
                return result;
            }
            log.debug(" __ 进入字典翻译切面 DictAspect —— ");
            String json = JSONObject.toJSONString(data);
            //update-begin--Author:scott -- Date:20211223 ----for：【issues/3303】restcontroller返回json数据后key顺序错乱 -----
            JSONObject item = JSONObject.parseObject(json, Feature.OrderedField);
            //update-end--Author:scott -- Date:20211223 ----for：【issues/3303】restcontroller返回json数据后key顺序错乱 -----

            //update-begin--Author:scott -- Date:20190603 ----for：解决继承实体字段无法翻译问题------
            //for (Field field : record.getClass().getDeclaredFields()) {
            // 遍历所有字段，把字典Code取出来，放到 map 里
            for (Field field : clazz.getDeclaredFields()) {
                String value = item.getString(field.getName());
                //update-end--Author:scott  -- Date:20190603 ----for：解决继承实体字段无法翻译问题------
                if (field.getAnnotation(Dict.class) != null) {
                    if (!dictFieldList.contains(field)) {
                        dictFieldList.add(field);
                    }
                    String code = field.getAnnotation(Dict.class).dictCode();
                    String text = field.getAnnotation(Dict.class).dictText();
                    String table = field.getAnnotation(Dict.class).dictTable();

                    List<String> dataList;
                    String dictCode = code;
                    if (!StringUtils.isEmpty(table)) {
                        dictCode = String.format("%s,%s,%s", table, text, code);
                    }
                    dataList = dataListMap.computeIfAbsent(dictCode, k -> new ArrayList<>());
//                    this.listAddAllDeduplicate(dataList, Arrays.asList(value.split(",")));
                }
                //date类型默认转换string格式化日期
                //update-begin--Author:zyf -- Date:20220531 ----for：【issues/#3629】 DictAspect Jackson序列化报错-----
                //if (JAVA_UTIL_DATE.equals(field.getType().getName())&&field.getAnnotation(JsonFormat.class)==null&&item.get(field.getName())!=null){
                //SimpleDateFormat aDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // item.put(field.getName(), aDate.format(new Date((Long) item.get(field.getName()))));
                //}
                //update-end--Author:zyf -- Date:20220531 ----for：【issues/#3629】 DictAspect Jackson序列化报错-----
            }
//            }

            //step.2 调用翻译方法，一次性翻译
//            Map<String, List<DictModel>> translText = this.translateAllDict(dataListMap);
//
//            //step.3 将翻译结果填充到返回结果里
//            for (Field field : dictFieldList) {
//                String code = field.getAnnotation(Dict.class).dictCode();
//                String text = field.getAnnotation(Dict.class).dictText();
//                String table = field.getAnnotation(Dict.class).dictTable();
//
//                String fieldDictCode = code;
//                if (!StringUtils.isEmpty(table)) {
//                    fieldDictCode = String.format("%s,%s,%s", table, text, code);
//                }
//                String value = item.getString(field.getName());
//                if (oConvertUtils.isNotEmpty(value)) {
//                    List<DictModel> dictModels = translText.get(fieldDictCode);
//                    if (dictModels == null || dictModels.size() == 0) {
//                        continue;
//                    }
//                    String textValue = this.translDictText(dictModels, value);
//                    item.put(field.getName() + CommonConstant.DICT_TEXT_SUFFIX, textValue);
//                }
//            }
//            ((Result) result).setResult(item);
        }

        return result;
    }

//    /**
//     * list 去重添加
//     */
//    private void listAddAllDeduplicate(List<String> dataList, List<String> addList) {
//        // 筛选出dataList中没有的数据
//        List<String> filterList = addList.stream().filter(i -> !dataList.contains(i)).collect(Collectors.toList());
//        dataList.addAll(filterList);
//    }

    /**
     * 一次性把所有的字典都翻译了
     * 1.  所有的普通数据字典的所有数据只执行一次SQL
     * 2.  表字典相同的所有数据只执行一次SQL
     *
     * @param dataListMap
     * @return
     */
//    private Map<String, List<DictModel>> translateAllDict(Map<String, List<String>> dataListMap) {
//        // 翻译后的字典文本，key=dictCode
//        Map<String, List<DictModel>> translText = new HashMap<>(5);
//        // 需要翻译的数据（有些可以从redis缓存中获取，就不走数据库查询）
//        List<String> needTranslData = new ArrayList<>();
//        //step.1 先通过redis中获取缓存字典数据
//        for (String dictCode : dataListMap.keySet()) {
//            List<String> dataList = dataListMap.get(dictCode);
//            if (dataList.size() == 0) {
//                continue;
//            }
//            // 表字典需要翻译的数据
//            List<String> needTranslDataTable = new ArrayList<>();
//            for (String s : dataList) {
//                String data = s.trim();
//                if (data.length() == 0) {
//                    continue; //跳过循环
//                }
//                if (dictCode.contains(",")) {
//                    String keyString = String.format("sys:cache:dictTable::SimpleKey [%s,%s]", dictCode, data);
//                    if (redisTemplate.hasKey(keyString)) {
//                        try {
//                            String text = oConvertUtils.getString(redisTemplate.opsForValue().get(keyString));
//                            List<DictModel> list = translText.computeIfAbsent(dictCode, k -> new ArrayList<>());
//                            list.add(new DictModel(data, text));
//                        } catch (Exception e) {
//                            log.warn(e.getMessage());
//                        }
//                    } else if (!needTranslDataTable.contains(data)) {
//                        // 去重添加
//                        needTranslDataTable.add(data);
//                    }
//                } else {
//                    String keyString = String.format("sys:cache:dict::%s:%s", dictCode, data);
//                    if (redisTemplate.hasKey(keyString)) {
//                        try {
//                            String text = oConvertUtils.getString(redisTemplate.opsForValue().get(keyString));
//                            List<DictModel> list = translText.computeIfAbsent(dictCode, k -> new ArrayList<>());
//                            list.add(new DictModel(data, text));
//                        } catch (Exception e) {
//                            log.warn(e.getMessage());
//                        }
//                    } else if (!needTranslData.contains(data)) {
//                        // 去重添加
//                        needTranslData.add(data);
//                    }
//                }
//
//            }
//            //step.2 调用数据库翻译表字典
//            if (needTranslDataTable.size() > 0) {
//                String[] arr = dictCode.split(",");
//                String table = arr[0], text = arr[1], code = arr[2];
//                String values = String.join(",", needTranslDataTable);
//                log.debug("translateDictFromTableByKeys.dictCode:" + dictCode);
//                log.debug("translateDictFromTableByKeys.values:" + values);
//                List<DictModel> texts = commonApi.translateDictFromTableByKeys(table, text, code, values);
//                log.debug("translateDictFromTableByKeys.result:" + texts);
//                List<DictModel> list = translText.computeIfAbsent(dictCode, k -> new ArrayList<>());
//                list.addAll(texts);
//
//                // 做 redis 缓存
//                for (DictModel dict : texts) {
//                    String redisKey = String.format("sys:cache:dictTable::SimpleKey [%s,%s]", dictCode, dict.getValue());
//                    try {
//                        // update-begin-author:taoyan date:20211012 for: 字典表翻译注解缓存未更新 issues/3061
//                        // 保留5分钟
//                        redisTemplate.opsForValue().set(redisKey, dict.getText(), 300, TimeUnit.SECONDS);
//                        // update-end-author:taoyan date:20211012 for: 字典表翻译注解缓存未更新 issues/3061
//                    } catch (Exception e) {
//                        log.warn(e.getMessage(), e);
//                    }
//                }
//            }
//        }
//
//        //step.3 调用数据库进行翻译普通字典
//        if (needTranslData.size() > 0) {
//            List<String> dictCodeList = Arrays.asList(dataListMap.keySet().toArray(new String[]{}));
//            // 将不包含逗号的字典code筛选出来，因为带逗号的是表字典，而不是普通的数据字典
//            List<String> filterDictCodes = dictCodeList.stream().filter(key -> !key.contains(",")).collect(Collectors.toList());
//            String dictCodes = String.join(",", filterDictCodes);
//            String values = String.join(",", needTranslData);
//            log.debug("translateManyDict.dictCodes:" + dictCodes);
//            log.debug("translateManyDict.values:" + values);
//            Map<String, List<DictModel>> manyDict = commonApi.translateManyDict(dictCodes, values);
//            log.debug("translateManyDict.result:" + manyDict);
//            for (String dictCode : manyDict.keySet()) {
//                List<DictModel> list = translText.computeIfAbsent(dictCode, k -> new ArrayList<>());
//                List<DictModel> newList = manyDict.get(dictCode);
//                list.addAll(newList);
//
//                // 做 redis 缓存
//                for (DictModel dict : newList) {
//                    String redisKey = String.format("sys:cache:dict::%s:%s", dictCode, dict.getValue());
//                    try {
//                        redisTemplate.opsForValue().set(redisKey, dict.getText());
//                    } catch (Exception e) {
//                        log.warn(e.getMessage(), e);
//                    }
//                }
//            }
//        }
//        return translText;
//    }

    /**
     * 字典值替换文本
     *
     * @param dictModels
     * @param values
     * @return
     */
//    private String translDictText(List<DictModel> dictModels, String values) {
//        List<String> result = new ArrayList<>();
//
//        // 允许多个逗号分隔，允许传数组对象
//        String[] splitVal = values.split(",");
//        for (String val : splitVal) {
//            String dictText = val;
//            for (DictModel dict : dictModels) {
//                if (val.equals(dict.getValue())) {
//                    dictText = dict.getText();
//                    break;
//                }
//            }
//            result.add(dictText);
//        }
//        return String.join(",", result);
//    }

    /**
     * 翻译字典文本
     *
     * @param code
     * @param text
     * @param table
     * @param key
     * @return
     */
//    @Deprecated
//    private String translateDictValue(String code, String text, String table, String key) {
//        if (oConvertUtils.isEmpty(key)) {
//            return null;
//        }
//        StringBuffer textValue = new StringBuffer();
//        String[] keys = key.split(",");
//        for (String k : keys) {
//            String tmpValue = null;
//            log.debug(" 字典 key : " + k);
//            if (k.trim().length() == 0) {
//                continue; //跳过循环
//            }
//            //update-begin--Author:scott -- Date:20210531 ----for： !56 优化微服务应用下存在表字段需要字典翻译时加载缓慢问题-----
//            if (!StringUtils.isEmpty(table)) {
//                log.debug("--DictAspect------dicTable=" + table + " ,dicText= " + text + " ,dicCode=" + code);
//                String keyString = String.format("sys:cache:dictTable::SimpleKey [%s,%s,%s,%s]", table, text, code, k.trim());
//                if (redisTemplate.hasKey(keyString)) {
//                    try {
//                        tmpValue = oConvertUtils.getString(redisTemplate.opsForValue().get(keyString));
//                    } catch (Exception e) {
//                        log.warn(e.getMessage());
//                    }
//                } else {
//                    tmpValue = commonApi.translateDictFromTable(table, text, code, k.trim());
//                }
//            } else {
//                String keyString = String.format("sys:cache:dict::%s:%s", code, k.trim());
//                if (redisTemplate.hasKey(keyString)) {
//                    try {
//                        tmpValue = oConvertUtils.getString(redisTemplate.opsForValue().get(keyString));
//                    } catch (Exception e) {
//                        log.warn(e.getMessage());
//                    }
//                } else {
//                    tmpValue = commonApi.translateDict(code, k.trim());
//                }
//            }
//            //update-end--Author:scott -- Date:20210531 ----for： !56 优化微服务应用下存在表字段需要字典翻译时加载缓慢问题-----
//
//            if (tmpValue != null) {
//                if (!"".equals(textValue.toString())) {
//                    textValue.append(",");
//                }
//                textValue.append(tmpValue);
//            }
//
//        }
//        return textValue.toString();
//    }

    /**
     * 检测返回结果集中是否包含Dict注解
     *
     * @param clazz
     * @return
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
