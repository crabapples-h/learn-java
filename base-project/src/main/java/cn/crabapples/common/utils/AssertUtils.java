package cn.crabapples.common.utils;

import cn.crabapples.common.ApplicationException;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public abstract class AssertUtils {
    /**
     * 断言为null
     *
     * @param object  需要断言判断的对象
     * @param message 断言失败提示
     */
    public static void isNull(Object object, String message) {
        if (Objects.nonNull(object)) {
            newException(message);
        }
    }

    /**
     * 断言不为null
     *
     * @param object  需要断言判断的对象
     * @param message 断言失败提示
     */
    public static void notNull(Object object, String message) {
        if (Objects.isNull(object)) {
            newException(message);
        }
    }

    /**
     * 断言值是否相等
     *
     * @param source  需要判断的值
     * @param target  需要等于的值
     * @param message 断言失败提示
     */
    public static void isEquals(@NotNull Object source, @NotNull Object target, String message) {
        if (!source.equals(target)) {
            newException(message);
        }
    }

    private static void newException(String message) {
        throw new ApplicationException(message);
    }

    public static <T> boolean isInstanceOf(Class<T> clazz, Object e) {
        return clazz.isInstance(e);
    }
}
