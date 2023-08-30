package cn.crabapples.common.config;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.AttributeConverter;
import java.util.List;

/**
 * TODO 用于将对象转换为json字符串存入数据库
 *
 * @author Ms.He
 * 2023/8/31 0:16
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class JpaConverterListJson implements AttributeConverter<List<Object>, String> {
    @Override
    public String convertToDatabaseColumn(List<Object> attribute) {
        return JSONObject.toJSONString(attribute);
    }

    @Override
    public List<Object> convertToEntityAttribute(String dbData) {
        return JSONObject.parseArray(dbData, Object.class);
    }
}
