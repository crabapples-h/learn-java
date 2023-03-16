package demo.jvm;//package demo.jvm;
//
//
//import jdk.internal.org.objectweb.asm.ClassWriter;
//import jdk.internal.org.objectweb.asm.Opcodes;
//
///**
// * 演示元空间内存溢出 jdk11无法演示
// *
// * @author Mr.He
// * 2022/5/30 22:20
// * pc-name mrhe
// *
// * -XX:MaxMetaspaceSize=8M
// */
//public class Demo10 extends ClassLoader {
//    public static void main(String[] args) throws InterruptedException {
//        Demo10 demo10 = new Demo10();
//        ClassWriter classWriter = new ClassWriter(0);
//        //字节码版本号,修饰符,类名,包名,父类,实现的接口名称
//        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Class", null, "java/lang/Object", null);
//        byte[] code = classWriter.toByteArray();
//        Class<?> aClass = demo10.defineClass("Class", code, 0, code.length);
//        System.out.println();
//        System.err.println(aClass);
//    }
//
//}
