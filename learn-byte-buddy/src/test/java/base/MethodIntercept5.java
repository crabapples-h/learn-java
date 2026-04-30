package base;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

public class MethodIntercept5 {
    @RuntimeType
    public void handler(@This Object targetObject) {
        System.err.println("MethodIntercept5：handler" + targetObject);
    }
}
