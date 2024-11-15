package base;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class MethodIntercept6 {
    @RuntimeType
    public Object handler(
            // 被拦截的目标对象,拦截静态方法时可用
            @Origin Class<?> clazz,
            // 被拦截的目标方法,拦截实例方法和静态方法时可用
            @Origin Method targetMethod,
            // 被拦截的目标方法的参数
            @AllArguments Object[] args,
            // 用于调用原方法
            // 若确定父类，也可用具体的父类来接受  @SuperCall TestSuperClass zuper
            @SuperCall Callable<?> zuper) throws Exception {
        System.out.println("MethodIntercept6 clazz:" + clazz);
        System.out.println("MethodIntercept6 targetMethod:" + targetMethod);
        System.out.println("MethodIntercept6 args:" + Arrays.toString(args));
        System.out.println("MethodIntercept6 zuper:" + zuper);
        // 调用原方法
        return zuper.call();
    }
}
