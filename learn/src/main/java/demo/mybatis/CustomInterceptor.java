package demo.mybatis;


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
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
@Slf4j
public class CustomInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("mybatis 自定义拦截器拦截开始");
        Object target =  invocation.getTarget();
        Method method = invocation.getMethod();
        Object[] args = invocation.getArgs();
        String className = target.getClass().getName();
        String methodName = method.getName();
        log.info("\n拦截器类:[{}]\n方法:[{}]\n参数:[{}]", className, methodName, Arrays.toString(args));
        log.info("\nmybatis自定义拦截器拦截结束");
        return invocation.proceed();

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    //
    @Override
    public void setProperties(Properties properties) {
    }
}
