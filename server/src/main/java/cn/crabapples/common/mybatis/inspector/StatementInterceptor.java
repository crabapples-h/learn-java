package cn.crabapples.common.mybatis.inspector;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


/**
 * StatementHandler SQL语句处理器
 * <p>
 * prepare	预处理 SQL 语句
 * parameterize	设置 SQL 参数
 * batch	执行批处理
 * update	执行更新操作
 * query	执行查询操作
 */
//@Component
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "getBoundSql", args = {}),
        @Signature(type = StatementHandler.class, method = "getParameterHandler", args = {}),
})
@Slf4j
public class StatementInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("\nStatement拦截器拦截开始");
        StatementHandler target = (StatementHandler) invocation.getTarget();
        Method method = invocation.getMethod();
        Object[] args = invocation.getArgs();

        String className = target.getClass().getName();
        String methodName = method.getName();

        if (methodName.equals("prepare")) {
            log.info("\n拦截器类:[{}]\n方法:[{}]\n参数:[{}]", className, methodName, Arrays.toString(args));
            log.info("\nStatement拦截器拦截结束");
            BoundSql boundSql = target.getBoundSql();
            String sql = boundSql.getSql();
            log.info("\n执行的SQL: [{}]", sql);
            MetaObject metaObject = SystemMetaObject.forObject(target);
            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
            String fullMapperId = mappedStatement.getId();
            String mapperClass = fullMapperId.substring(0, fullMapperId.lastIndexOf("."));
            String mapperMethod = fullMapperId.substring(fullMapperId.lastIndexOf(".") + 1);
            List<ResultMap> resultMaps = mappedStatement.getResultMaps();
            log.info("\nMapperId: [{}]\nMapper类: [{}]\nMapper方法: [{}]", fullMapperId, mapperClass, mapperMethod);
            if (!resultMaps.isEmpty()) {
                ResultMap resultMap = resultMaps.get(0);
                log.info("\n返回结果集: [{}]", resultMap.getId());
                Class<?> type = resultMap.getType();
                log.info("\n返回结果集: [{}]", type);
                Field[] fields = type.getDeclaredFields();
                log.info("\n返回结果集字段: [{}]", Arrays.asList(fields));
            }
            log.info("\nMapperId: [{}]\nMapper类: [{}]\nMapper方法: [{}]", fullMapperId, mapperClass, mapperMethod);


            log.info("\n----------------------------\n");

        }

        return invocation.proceed();

    }

}
