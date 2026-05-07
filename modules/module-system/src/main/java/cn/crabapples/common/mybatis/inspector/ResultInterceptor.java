package cn.crabapples.common.mybatis.inspector;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


/**
 * ResultSetHandler 结果集处理器
 * <p>
 * handleResultSets	处理查询结果
 * handleOutputParameters	处理存储过程的输出参数
 */
@Component
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}),
})
@Slf4j
public class ResultInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("\nResult拦截器拦截开始");
        ResultSetHandler target = (ResultSetHandler) invocation.getTarget();
        Method method = invocation.getMethod();
        Object[] args = invocation.getArgs();
        String className = target.getClass().getName();
        String methodName = method.getName();
        log.info("\n拦截器类:[{}]\n方法:[{}]\n参数:[{}]", className, methodName, Arrays.toString(args));
        List<?> records = (List<?>) invocation.proceed();
        for (Object record : records) {
            System.err.println(record);
        }
        log.info("\nResult拦截器拦截结束");
        log.info("\n----------------------------\n");
        return records;
    }
}
