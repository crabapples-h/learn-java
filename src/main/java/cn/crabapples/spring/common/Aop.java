package cn.crabapples.spring.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * TODO
 *
 * @author Mr.He
 * 2020/1/30 18:07
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Aspect
@Configuration
@Order(50)
//@SuppressWarnings("unchecked")
public class Aop {
    private static final String SERVICE_AOP = "execution(* cn.crabapples.spring.service.*.*(..))";

    @Pointcut(SERVICE_AOP)
    public void serviceAop() {
    }

    @Around("serviceAop()")
    public Object serviceAop(ProceedingJoinPoint join) throws Throwable {
        System.err.println(join);
        System.err.println("SERVICE_AOP()");
//		MethodSignature method = (MethodSignature) join.getSignature();
//		System.out.println(method.getName());
        Object obj = join.proceed();
        return obj;
    }

}
