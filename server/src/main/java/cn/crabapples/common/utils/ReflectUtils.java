package cn.crabapples.common.utils;

import cn.crabapples.common.base.BaseEntity;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ReflectUtils {
    /**
     * 获取class文件的所有属性，包括继承的属性
     *
     * @param clazz 需要获取属性的class文件
     * @return class文件中所有的属性
     */
    public static Field[] getAllFields(Class<?> clazz) {
//        if (clazz != BaseEntity.class) {
//            return new Field[0];
//        }
        Field[] superFields = null;
        // 获取class的父类
        Class<?> superclass = clazz.getSuperclass();
        if (Object.class != superclass) {
            // 当父类不是Object时获取父类class的属性
            superFields = getAllFields(superclass);
        }
        // 获取当前class的属性
        Field[] fields = clazz.getDeclaredFields();
        if (superFields != null) {
            // 当父类中有属性时，调用数组的copyOf方法，将原有属性复制到新的数组里
            // function1 通过jvm的native方法进行合并
            Field[] arrays = mergeFieldArray1(fields, superFields);
            // function2 通过循环的的方式进行合并
            Field[] arrays2 = mergeFieldArray2(fields, superFields);
            return arrays;
        }
        return fields;
    }

    /**
     * 合并两个数组(native)
     *
     * @param source1 需要合并的数组
     * @param source2 需要合并的数组
     * @return 合并后的数组
     */
    private static Field[] mergeFieldArray1(Field[] source1, Field[] source2) {
        Field[] target = new Field[source1.length + source2.length];
        /*
         * 调用jvm里的native的方法进行拷贝数组
         * 参数为：
         *  原数组
         *  原数组需要从第几个元素开始拷贝
         *  目标数组
         *  存放到目标数组的第几个元素
         *  需要拷贝的长度
         */
        System.arraycopy(source1, 0, target, 0, source1.length);
        System.arraycopy(source2, 0, target, source1.length, source2.length);
        return target;
    }

    /**
     * 合并两个数组(循环)
     *
     * @param source1 需要合并的数组
     * @param source2 需要合并的数组
     * @return 合并后的数组
     */
    private static Field[] mergeFieldArray2(Field[] source1, Field[] source2) {
        // 调用Arrays.copyOf方法创建一个新的数组，第一个参数为原数组，第二个参数为新数组的长度
        Field[] target = Arrays.copyOf(source1, source1.length + source2.length);
        // 循环将第二个数组中的元素放入拷贝后的数组中第一个数组的后面
        for (int i = source1.length; i < target.length; i++) {
            target[i] = source2[i - source1.length];
        }
        return target;
    }

}
