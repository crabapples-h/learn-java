//package org.example.generator;
//
//import com.google.auto.service.AutoService;
//import javassist.*;
//
//import javax.annotation.processing.*;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.Element;
//import javax.lang.model.element.TypeElement;
//import javax.tools.Diagnostic;
//import java.io.IOException;
//import java.util.LinkedHashSet;
//import java.util.Set;
//
//@AutoService(Processor.class)
//public class CrabapplesControllerProcessorJavassist extends AbstractProcessor {
//
//    //支持的注解类型
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        Set<String> annotataions = new LinkedHashSet<>();
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
//
//    }
//    /*
//        mvn -X -U -DskipTests=true clean compile package install -f code-generator/pom.xml &&  mvn -X -U -DskipTests=true clean compile package install -f serve/pom.xml&& javap -c src.main.java.org.example.application.system.template.entity.DemoController
//        mvn  -DskipTests=true clean compile package install -f code-generator/pom.xml &&  mvn  -DskipTests=true clean compile package install -f serve/pom.xml&& javap -c src.main.java.org.example.application.system.template.entity.DemoController
//     */
//
//    @Override
////    @Getter
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, "执行process()方法");
//        System.err.println("start EnableTestAnno.process()");
//        for (Element rootElement : roundEnv.getRootElements()) {
//            System.err.println(rootElement);
//        }
//
//        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(CrabapplesController.class);
//        for (Element element : elementsAnnotatedWith) {
//            try {
//                TypeElement typeElement = ((TypeElement) element);
//                String className = typeElement.getQualifiedName().toString();
//                System.err.println(className);
//                createByJavassist(className);
//                System.err.println("---------------------------end");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }
//
//
//    private void createByJavassist(String className) throws CannotCompileException, NotFoundException, IOException {
//        String newFileName = className + "Controller";
//
//        ClassPool pool = ClassPool.getDefault();
//        pool.appendClassPath(new LoaderClassPath(getClass().getClassLoader()));
//        CtClass ctClass = pool.get(className);
//        boolean frozen = ctClass.isFrozen();
//        messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, String.format("当前文件锁定状态:[%s]", frozen));
//        if (frozen) {
//            ctClass.defrost();
//        }
//        CtMethod method = CtNewMethod.make("public void hello() {" + "    System.out.println(\"hello, world,Javassist\");" + "}", ctClass);
//        ctClass.addMethod(method);
//        ctClass.setName(newFileName);
//        ctClass.writeFile("target/classes/");
//    }
//}
