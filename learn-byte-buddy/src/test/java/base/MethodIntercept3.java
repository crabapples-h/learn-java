package base;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class MethodIntercept3 {
    @RuntimeType
    public Object handler(
            // 被拦截的目标对象,拦截实例方法或构造方法时可用
            @This Object targetObject,
            // 被拦截的目标方法,拦截实例方法和静态方法时可用
            @Origin Method targetMethod,
            // 被拦截的目标方法的参数
            @AllArguments Object[] args,
            // 被拦截的目标对象，拦截实例方法或构造方法时可用
            @Super Object targetObject2,

            // 用于调用原方法
            // 若确定父类，也可用具体的父类来接受  @SuperCall TestSuperClass zuper
            @SuperCall Callable<?> zuper) throws Exception {
        System.out.println("MethodIntercept3 targetObject:" + targetObject);
        System.out.println("MethodIntercept3 targetMethod:" + targetMethod);
        System.out.println("MethodIntercept3 args:" + Arrays.toString(args));
        System.out.println("MethodIntercept3 targetObject2:" + targetObject2);
        System.out.println("MethodIntercept3 zuper:" + zuper);
        // 调用原方法
        return zuper.call();
    }
}
