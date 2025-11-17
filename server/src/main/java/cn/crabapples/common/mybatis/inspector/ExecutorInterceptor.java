package cn.crabapples.common.mybatis.inspector;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Properties;


/**
 * Executor SQL执行器
 * <p>
 * update	执行增删改操作
 * query	执行查询操作
 * commit	提交事务
 * rollback	回滚事务
 */
//@Component
@Intercepts({
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
})
@Slf4j
public class ExecutorInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("\nExecutor拦截器拦截开始");
        Executor target = (Executor) invocation.getTarget();
        Method method = invocation.getMethod();
        String className = target.getClass().getName();
        String methodName = method.getName();
        Object[] args = invocation.getArgs();
        log.info("\n拦截器类:[{}]\n方法:[{}]\n参数:[{}]", className, methodName, Arrays.toString(args));
        log.info("\nExecutor拦截器拦截结束");
        log.info("\n----------------------------\n");
        return invocation.proceed();
    }
}
