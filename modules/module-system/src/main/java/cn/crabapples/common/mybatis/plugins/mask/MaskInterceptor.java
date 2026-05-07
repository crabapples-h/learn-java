package cn.crabapples.common.mybatis.plugins.mask;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;

/**
 * TODO 数据脱敏插件
 *
 * @author Ms.He
 * 2025-01-13 11:19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Component
@Intercepts(@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}))
@Slf4j
public class MaskInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        List<?> records = (List<?>) invocation.proceed();
        for (Object record : records) {
            Class<?> aClass = record.getClass();
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(CMask.class)) {
                    MetaObject metaObject = SystemMetaObject.forObject(record);
                    CMaskEnum maskType = field.getAnnotation(CMask.class).value();
                    String fieldName = field.getName();
                    Object value = metaObject.getValue(fieldName);
                    if (value instanceof String) {
                        Object mask = maskType.mask((String) value);
                        metaObject.setValue(fieldName, mask);
                    }
                }
            }
        }
        return records;
    }
}
