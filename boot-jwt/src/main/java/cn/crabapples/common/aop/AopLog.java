package cn.crabapples.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * TODO service切面拦截
 *
 * @author Mr.He
 * 2020/1/30 18:07
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public class AopLog {
    private static final Logger logger = LoggerFactory.getLogger(AopLog.class);

    public AopLog() {
        logger.info("注入日志切面:[{}]", "AopConfigure");
    }


    /**
     * Object[] getArgs()：返回此连接点处（目标方法）的参数
     * Signature getSignature()：返回连接点处的签名。
     * Object getTarget()：返回目标对象
     * Object getThis()：返回当前正在执行的对象
     * StaticPart getStaticPart()：返回一个封装此连接点的静态部分的对象。
     * SourceLocation getSourceLocation()：返回与连接点对应的源位置
     * String toLongString()：返回连接点的扩展字符串表示形式。
     * String toShortString()：返回连接点的缩写字符串表示形式。
     * String getKind()：返回表示连接点类型的字符串
     */
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        Object target = joinPoint.getTarget();
        Object aThis = joinPoint.getThis();
        JoinPoint.StaticPart staticPart = joinPoint.getStaticPart();
        SourceLocation sourceLocation = joinPoint.getSourceLocation();
        String longString = joinPoint.toLongString();
        String shortString = joinPoint.toShortString();

        System.out.println("【前置通知】");
        System.out.println("\targs=" + Arrays.asList(args));
        System.out.println("\tsignature=" + signature);
        System.out.println("\ttarget=" + target);
        System.out.println("\taThis=" + aThis);
        System.out.println("\tstaticPart=" + staticPart);
        System.out.println("\tsourceLocation=" + sourceLocation);
        System.out.println("\tlongString=" + longString);
        System.out.println("\tshortString=" + shortString);
    }

    public void after(JoinPoint joinPoint) {
        System.out.println("【后置通知】");
        System.out.println("\tkind=" + joinPoint.getKind());
    }

    /**
     * 返回通知：目标方法返回后执行以下代码
     * value 属性：绑定通知的切入点表达式。可以关联切入点声明，也可以直接设置切入点表达式
     * pointcut 属性：绑定通知的切入点表达式，优先级高于 value，默认为 ""
     * returning 属性：通知签名中要将返回值绑定到的参数的名称，默认为 ""
     *
     * @param joinPoint ：提供对连接点处可用状态和有关它的静态信息的反射访问
     * @param result    ：目标方法返回的值，参数名称与 returning 属性值一致。无返回值时，这里 result 会为 null.
     */
    public void afterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("【返回通知】");
        System.out.println("\t目标方法返回值=" + result);
    }

    /**
     * 异常通知：目标方法发生异常的时候执行以下代码，此时返回通知不会再触发
     * value 属性：绑定通知的切入点表达式。可以关联切入点声明，也可以直接设置切入点表达式
     * pointcut 属性：绑定通知的切入点表达式，优先级高于 value，默认为 ""
     * throwing 属性：与方法中的异常参数名称一致，
     *
     * @param ex：捕获的异常对象，名称与 throwing 属性值一致
     */
    public void afterThrowing(JoinPoint jp, Exception ex) {
        String methodName = jp.getSignature().getName();
        System.out.println("【异常通知】");
        if (ex instanceof ArithmeticException) {
            System.out.println("\t【" + methodName + "】方法算术异常（ArithmeticException）：" + ex.getMessage());
        } else {
            System.out.println("\t【" + methodName + "】方法异常：" + ex.getMessage());
        }
    }

    public Object around(ProceedingJoinPoint join) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) join.getSignature();
        Method method = methodSignature.getMethod();
        logger.info("切面拦截成功,拦截方法:[{}],参数:[{}]", method.getName(), join.getArgs());
        Object result = join.proceed();
        logger.info("切面拦截结束,返回值:[{}]", result);
        return result;
    }

}
