//package ast.dic;
//
//import javassist.*;
//
//import javax.annotation.processing.*;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.Element;
//import javax.lang.model.element.TypeElement;
//import javax.tools.Diagnostic;
//import java.util.LinkedHashSet;
//import java.util.Set;
//
////@AutoService(Processor.class)
////@Getter
////@SupportedSourceVersion(SourceVersion.RELEASE_8)
////@SupportedAnnotationTypes("ast.dic.TestAnno")
//public class EnableTestAnnoBak extends AbstractProcessor {
//    //支持的注解类型
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        Set<String> annotataions = new LinkedHashSet<>();
////        annotataions.add(TestAnno.class.getCanonicalName());
//        annotataions.add(CrabapplesController.class.getCanonicalName());
//        return annotataions;
//    }
//
//    private Filer filer;
//    private Messager messager;
//
//    //支持的java版本
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.latestSupported();
//    }
//
//    public synchronized void init(ProcessingEnvironment processingEnv) {
//        this.filer = processingEnv.getFiler();
//        this.messager = processingEnv.getMessager();
//        super.init(processingEnv);
//    }
//
//    //    @Override
////    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
////        System.err.println("start EnableTestAnno.process()");
////        for (TypeElement annotation : annotations) {
////            // 获取所有被该注解 标记过的实例
////            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
////
////            // 按照需求 检查注解使用的是否正确 以set开头，并且参数只有一个
////            Map<Boolean, List<Element>> annotatedMethods = annotatedElements.stream().collect(
////                    Collectors.partitioningBy(element ->
////                            ((ExecutableType) element.asType()).getParameterTypes().size() == 1
////                                    && element.getSimpleName().toString().startsWith("set")));
////            System.err.println(annotatedMethods);
////        }
////        return true;
////    }
//    private String createDemoCode() {
//        return "public String list()" +
//                " " +
//                " {\n" +
//                "    return \"\"" +
//                ";\n" +
//                "}\n";
//    }
//
//    @Override
////    @Getter
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, "执行process()方法");
//        ClassPool pool = ClassPool.getDefault();
////        pool.appendClassPath(new LoaderClassPath(getClass().getClassLoader()));
//        pool.insertClassPath(new ClassClassPath(this.getClass())); // 添加当前类的类路径
//        System.err.println("start EnableTestAnno.process()");
////        System.err.println(roundEnv.getRootElements());
//        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(CrabapplesController.class);
//        for (Element element : elementsAnnotatedWith) {
//            try {
//                TypeElement typeElement = ((TypeElement) element);
//                String className = typeElement.getQualifiedName().toString();// ast.dic.DemoController
//
//
////                new ByteBuddy()
//////                        .subclass(Object.class)
////                        .redefine(Class.forName(className))
////                        .method(ElementMatchers.named("hello1"))
////                        .intercept(FixedValue.value("Hello, world1!"))
////                        .make()
////                        .load(ClassLoader.getSystemClassLoader())
////                        .saveIn(new File("ast.dic.aaa.class"));
////                messager.printMessage(Diagnostic.Kind.NOTE, String.format("当前文件类名:[%s]", className));
////                messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, String.format("当前文件类名:[%s]", className));
////                System.err.println(element.getEnclosingElement());// ast.dic
////                String packageName = ((TypeElement) element.getEnclosingElement()).getQualifiedName().toString(); // 获取包名
////                System.err.println("-------------start");
//////                System.err.println(element.getKind());// CLASS
////                CtClass ctClass = pool.get(className);
//                CtClass ctClass = pool.get(className);
//                boolean frozen = ctClass.isFrozen();
//                messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, String.format("当前文件锁定状态:[%s]", frozen));
//                if (frozen) {
//                    ctClass.defrost();
//                }
////
////                 添加新方法
//                CtMethod method = CtNewMethod.make(
//                        "public void hello() {" +
//                                "    System.out.println(\"hello, world\");" +
//                                "}", ctClass);
//                ctClass.addMethod(method);
////                defaultCtor.insertBefore("long start = 100L;");
////                defaultCtor.insertBefore("long start = System.nanoTime();");
////                defaultCtor.insertAfter("System.out.println(\"Class " + className + " construction took \" + (System.nanoTime() ) + \" ns\");", false);
//
//                ctClass.toClass();
//                // 写回修改后的字节码
//                ctClass.writeFile("ast.dic.aaa.class");
////                JavaFileObject sourceFile = filer.createSourceFile(fileName);
////                System.err.println(fileName);
////                System.err.println(sourceFile);
////
////                Writer writer = sourceFile.openWriter();
////                writer.write(String.format("package %s;\n\n", element.getEnclosingElement()));
////                writer.write(String.format("public class %s {\n", fileName));
////                writer.write("\n");
////                writer.write("    " + createDemoCode());
////                writer.write("\n");
////                writer.write("}\n");
////                writer.close();
////            new ExecutableElement()
////            try {
////                JavaFileObject builderFile = processingEnv.getFiler()
////                        .createSourceFile("builderClassName");
////                System.err.println(builderFile);
////            } catch (IOException e) {
////                throw new RuntimeException(e);
////            }
////            final ExecutableElement executableElement = new MethodSymbol();
////                System.err.println(element.getClass());
////                System.err.println(element);
////                System.err.println(element.getEnclosedElements());
////                System.err.println(element.getEnclosingElement());
////                System.err.println(element.asType());
////                System.err.println(element.getKind());
////                System.err.println(element.getModifiers());
////                System.err.println(element.getSimpleName());
//                System.err.println("---------------------------end");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
////        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
////                "@BuilderProperty 注解必须放到方法上并且是set开头的单参数方法");
//        return true;
//    }
//
//
//}
