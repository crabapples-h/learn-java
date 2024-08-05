//package org.example.generator;
//
//import net.bytebuddy.ByteBuddy;
//import net.bytebuddy.description.annotation.AnnotationDescription;
//import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
//import net.bytebuddy.implementation.FixedValue;
//import net.bytebuddy.matcher.ElementMatchers;
//
//public class ByteBuddyExample {
//
//    public static void main(String[] args) throws Exception {
//        // 创建注解描述
////        AnnotationDescription myAnnotation1 = AnnotationDescription.Builder.ofType(MyAnnotation1.class)
////                .define("value", "annotationValue1").build();
//
////        AnnotationDescription myAnnotation2 = AnnotationDescription.Builder.ofType(MyAnnotation2.class)
////                .define("value", "annotationValue2").build();
//
//        // 使用 ByteBuddy 创建带有注解的方法
//        Class<?> dynamicType = new ByteBuddy()
//                .subclass(Object.class)
//                .name("GeneratedClass")
//                .method(ElementMatchers.named("method1"))
//                .intercept(FixedValue.value("Hello from method1"))
////                .annotateMethod(myAnnotation1)
//                .defineMethod("method2", String.class)
//                .intercept(FixedValue.value("Hello from method2"))
////                .annotateMethod(myAnnotation2)
//                .make()
//                .load(ByteBuddyExample.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
//                .getLoaded();
//
//        // 创建实例并调用方法
//        Object instance = dynamicType.getDeclaredConstructor().newInstance();
//        System.out.println(dynamicType.getMethod("method1").invoke(instance));
//        System.out.println(dynamicType.getMethod("method2").invoke(instance));
//
//        // 打印方法注解
////        System.out.println(dynamicType.getMethod("method1").getAnnotation(MyAnnotation1.class).value());
////        System.out.println(dynamicType.getMethod("method2").getAnnotation(MyAnnotation2.class).value());
//    }
//}
