package cn.crabapples.common.mybatis;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;


/**
 * 文档写在语雀里
 * Executor SQL执行器
 * StatementHandler SQL语句处理器
 * ParameterHandler 参数处理器
 * ResultSetHandler 结果集处理器
 */
//@Component
@Intercepts({
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "getBoundSql", args = {}),
        @Signature(type = StatementHandler.class, method = "getParameterHandler", args = {}),
        @Signature(type = ParameterHandler.class, method = "getParameterObject", args = {}),
        @Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class}),
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}),
})
@Slf4j
public class CustomInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("\nmybatis 自定义拦截器拦截开始");
        Object target = invocation.getTarget();
        Method method = invocation.getMethod();
        Object[] args = invocation.getArgs();

        String className = target.getClass().getName();
        String methodName = method.getName();


        log.info("\n拦截器类:[{}]\n方法:[{}]\n参数:[{}]", className, methodName, Arrays.toString(args));
        log.info("\nmybatis 自定义拦截器拦截结束");

        log.info("\n----------------------------\n");

//        System.err.println(args[0]);
//        PreparedStatement ps = (PreparedStatement) args[0];
//        System.err.println(ps);


//        Object parameter = args[1];
//        System.err.println(parameter);

//        handler.setParameters(ps);
//        Object[] args = invocation.getArgs();
//        MappedStatement mappedStatement = (MappedStatement) args[0];
//        Object object = args[1];
//        RowBounds rowBounds = (RowBounds) args[2];
//        ResultHandler resultHandler = (ResultHandler)args[3];
//        Transaction transaction = executor.getTransaction();
//        Object parameterObject = parameterHandler.getParameterObject();
//        System.err.println(handler);
//        Object parameterObject = handler.getBoundSql().getParameterObject();
//        System.err.println(parameterObject);
//
//        BoundSql boundSql = handler.getBoundSql();
//        String sql = boundSql.getSql() + " and id = -10010";
//
//        Field sqlField = boundSql.getClass().getDeclaredField("sql");
//        sqlField.setAccessible(true);
//        sqlField.set(boundSql, sql);
//
//        // 使用反射设置修改后的 SQL
//        Field field = handler.getClass().getDeclaredField("boundSql");
//        field.setAccessible(true);
//        field.set(handler, boundSql);

//        System.err.println(args);
//        System.err.println(transaction);
        //第一种，性能高
        // if(parameterObject instanceof BaseModel){
        //  BaseModel baseModel = (BaseModel) parameterObject;
        //  baseModel.setLastUpdateBy(LocalUserUtil.getLocalUser().getNickName());
        // }
        //第二种使用反射处理，扒光撕开
//        Field lastUpdateBy = ReflectUtil.getField(parameterObject.getClass(), "lastUpdateBy");
//        if (lastUpdateBy != null) {
//            ReflectUtil.setFieldValue(parameterObject, lastUpdateBy, LocalUserUtil.getLocalUser().getNickName());
//        }
        return invocation.proceed();

    }

    @Override
    public Object plugin(Object target) {
        System.err.println(target);
        return Plugin.wrap(target, this);
    }

    //
    @Override
    public void setProperties(Properties properties) {
        System.err.println(properties);
        // 可选：读取配置文件中的属性
    }


//    /**
//     * 自定义 BoundSql，替换 SQL 内容
//     */
//    private static class CustomBoundSql extends BoundSql {
//        private final String newSql;
//
//        public CustomBoundSql(BoundSql boundSql, String newSql) {
//            super(boundSql.getConfiguration(), newSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
//            this.newSql = newSql;
//        }
//
//        @Override
//        public String getSql() {
//            return newSql;
//        }
//    }
}
