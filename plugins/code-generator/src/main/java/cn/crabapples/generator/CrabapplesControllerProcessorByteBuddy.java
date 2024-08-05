//package org.example.generator;
//
//import com.google.auto.service.AutoService;
//import javassist.*;
//import net.bytebuddy.ByteBuddy;
//import net.bytebuddy.description.annotation.AnnotationDescription;
//import net.bytebuddy.description.modifier.Visibility;
//import net.bytebuddy.dynamic.DynamicType;
//import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
//import net.bytebuddy.implementation.FieldAccessor;
//import net.bytebuddy.implementation.FixedValue;
//import net.bytebuddy.implementation.MethodDelegation;
//import net.bytebuddy.implementation.bind.annotation.RuntimeType;
//import net.bytebuddy.implementation.bind.annotation.SuperCall;
//import net.bytebuddy.matcher.ElementMatchers;
//import org.example.base.ResponseDTO;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.processing.*;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.Element;
//import javax.lang.model.element.TypeElement;
//import javax.tools.Diagnostic;
//import javax.tools.JavaFileObject;
//import java.io.File;
//import java.io.IOException;
//import java.io.Writer;
//import java.util.LinkedHashSet;
//import java.util.Set;
//import java.util.concurrent.Callable;
//
//@AutoService(Processor.class)
//public class CrabapplesControllerProcessorByteBuddy extends AbstractProcessor {
//
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
//
//    }
//
//    /*
//
//        mvn -X -U -DskipTests=true clean compile package install -f code-generator/pom.xml &&  mvn -X -U -DskipTests=true clean compile package install -f serve/pom.xml&& javap -c src.main.java.org.example.application.system.template.entity.DemoController
//        mvn  -DskipTests=true clean compile package install -f code-generator/pom.xml &&  mvn  -DskipTests=true clean compile package install -f serve/pom.xml&& javap -c src.main.java.org.example.application.system.template.entity.DemoController
//
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
//                createByByteBuddy(className);
//                System.err.println("---------------------------end");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }
//
//
//    private void createByByteBuddy(String className) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        String newFileName = className + "Controller";
//        System.err.println(newFileName);
//        new ByteBuddy()
//                .subclass(Object.class)
////                .rebase(BaseController.class)
////                .subclass(BaseController.class)
////                .rebase(Object.class)
////                .subclass(Object.class)
////                .redefine(Object.class)
////                .subclass(BaseController.class)
////                .rebase(ControllerTemplate.class)
////                .rebase(Class.forName(newFileName))
//                .name(newFileName)
//                // 添加注解
//                .annotateType(AnnotationDescription.Builder.ofType(RestController.class).build())
//                .annotateType(AnnotationDescription.Builder.ofType(RequestMapping.class).define("name", "/api/**").build())
//                // 添加属性
//                .defineField("field1", String.class, Visibility.PRIVATE)
//                .defineField("field2", String.class, Visibility.PRIVATE)
//
//                // 添加List方法
//                .defineMethod("list", ResponseDTO.class, Visibility.PUBLIC)
//                .intercept(MethodDelegation.to(DynamicBehavior.class))
//                .annotateMethod(AnnotationDescription.Builder
//                        .ofType(GetMapping.class)
//                        .define("name", "/list")
//                        .build())
//
//                // 添加Page方法
//                .defineMethod("page", ResponseDTO.class, Visibility.PUBLIC)
//                .intercept(MethodDelegation.to(DynamicBehavior.class))
//                .annotateMethod(AnnotationDescription.Builder
//                        .ofType(GetMapping.class)
//                        .define("name", "/page")
//                        .build())
//
//                // 添加findById方法
//                .defineMethod("detail", ResponseDTO.class, Visibility.PUBLIC)
//                .intercept(MethodDelegation.to(DynamicBehavior.class))
//                .annotateMethod(AnnotationDescription.Builder
//                        .ofType(GetMapping.class)
//                        .define("name", "/detail/{id}")
//                        .build())
//
//                // 添加removed方法
//                .defineMethod("remove", ResponseDTO.class, Visibility.PUBLIC)
//                .intercept(MethodDelegation.to(DynamicBehavior.class))
//                .annotateMethod(AnnotationDescription.Builder
//                        .ofType(DeleteMapping.class)
//                        .define("name", "/remove/{id}")
//                        .build())
//
//                // 添加构造方法
//                .method(ElementMatchers.isConstructor())
//                .intercept(FixedValue.value("Hello, world,ByteBuddy"))
//                .make()
//                .load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.CHILD_FIRST)
//                .saveIn(new File("src/main/java/"));
////                .saveIn(new File("target/classes/"));
//    }
//
//    public static class DynamicBehavior {
//        @RuntimeType
//        public ResponseDTO intercept(@SuperCall Callable<Object> zuper) throws Exception {
//            // 这里可以根据需要动态改变返回值，这里简单返回一个字符串
//            return new ResponseDTO("Hello, world,Javassist");
//        }
//    }
//}
