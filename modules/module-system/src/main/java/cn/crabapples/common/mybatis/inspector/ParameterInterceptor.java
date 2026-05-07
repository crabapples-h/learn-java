package cn.crabapples.common.mybatis.inspector;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.Properties;


/**
 * ParameterHandler 参数处理器
 * <p>
 * getParameterObject	获取参数对象
 * setParameters	设置参数对象
 */
//@Component
@Intercepts({
        @Signature(type = ParameterHandler.class, method = "getParameterObject", args = {}),
        @Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class}),
})
@Slf4j
public class ParameterInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("\nParameter拦截器拦截开始");
        ParameterHandler target = (ParameterHandler)invocation.getTarget();
        Method method = invocation.getMethod();
        Object[] args = invocation.getArgs();

        String className = target.getClass().getName();
        String methodName = method.getName();


        log.info("\n拦截器类:[{}]\n方法:[{}]\n参数:[{}]", className, methodName, Arrays.toString(args));
        log.info("\nParameter拦截器拦截结束");
        log.info("\n----------------------------\n");
        return invocation.proceed();
    }
}
