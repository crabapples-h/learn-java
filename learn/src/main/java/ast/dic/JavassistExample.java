//package ast.dic;
//import javassist.*;
//
//public class JavassistExample {
//
//    public static void main(String[] args) throws NotFoundException, CannotCompileException {
//        // 创建 ClassPool 实例，用于管理类
//        ClassPool pool = ClassPool.getDefault();
//
//        // 从 ClassPool 中获取 CtClass 对象，表示要修改的类
//        CtClass ctClass = pool.get("ast.dic.MyClass");
//
//        // 添加新方法
//        CtMethod newMethod = CtNewMethod.make(
//                "public void addedMethod() {" +
//                        "System.out.println(\"This method was dynamically added using Javassist!\");" +
//                        "}", ctClass);
//
//        // 将新方法添加到类中
//        ctClass.addMethod(newMethod);
//
//        // 生成修改后的字节码并加载到 JVM
//        ctClass.toClass();
//
//        // 创建原类的实例并调用新方法
//        MyClass myInstance = new MyClass();
//        myInstance.addedMethod(); // 运行时会打印 "This method was dynamically added using Javassist!"
//    }
//}
//
//// 假设这是你要修改的类
//class MyClass {
//    // ... 其他代码 ...
//}
