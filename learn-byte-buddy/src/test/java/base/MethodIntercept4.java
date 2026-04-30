package base;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Morph;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class MethodIntercept4 {
    @RuntimeType
    public Object handler(
            // 被拦截的目标方法的参数
            @AllArguments Object[] args,
            // 使用@Morph动态修改入参
            @Morph MyCallable zuper) {
        if (args != null && args.length > 0) {
            System.out.println("原始入参:" + args[0]);
            args[0] = (int) args[0] + 1;
            System.out.println("修改后的入参:" + args[0]);

        }
        Object call = zuper.call(args);
        return call;
    }
}
