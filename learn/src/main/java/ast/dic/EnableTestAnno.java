package ast.dic;

import javassist.*;
import lombok.Setter;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

//@AutoService(Processor.class)
//@Getter
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
//@SupportedAnnotationTypes("ast.dic.TestAnno")
public class EnableTestAnno extends AbstractProcessor {
    //支持的注解类型
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<>();
//        annotataions.add(TestAnno.class.getCanonicalName());
        annotataions.add(CrabapplesController.class.getCanonicalName());
        return annotataions;
    }

    private Filer filer;
    private Messager messager;

    //支持的java版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    public synchronized void init(ProcessingEnvironment processingEnv) {
        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
        super.init(processingEnv);
    }

    @Override
//    @Getter
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, "执行process()方法");
        System.err.println("start EnableTestAnno.process()");
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(CrabapplesController.class);
        for (Element element : elementsAnnotatedWith) {
            try {
                TypeElement typeElement = ((TypeElement) element);
                String className = typeElement.getQualifiedName().toString();// ast.dic.DemoController
                System.err.println(className);
//                createByNative(className, element);
//                createByJavassist(className);
                createByByteBuddy(className);
                System.err.println("---------------------------end");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void createByNative(String className, Element element) throws IOException {
        int lastIndex = className.lastIndexOf(".");
        String simpleClassName = className.substring(lastIndex + 1);
        String newFileName = simpleClassName;
        JavaFileObject sourceFile = filer.createSourceFile(newFileName);
        System.err.println(className);
        System.err.println(sourceFile);
        Writer writer = sourceFile.openWriter();
        writer.write(String.format("package %s;\n\n", element.getEnclosingElement()));
        writer.write(String.format("public class %s {\n", newFileName));
        writer.write("\n");
        writer.write("public void hello() {" +
                "    System.out.println(\"hello, world,native\");" +
                "}");
        writer.write("\n");
        writer.write("}\n");
        writer.close();
    }

    private void createByJavassist(String className) throws CannotCompileException, NotFoundException, IOException {
        String newFileName = className + "Controller";

        ClassPool pool = ClassPool.getDefault();
        pool.appendClassPath(new LoaderClassPath(getClass().getClassLoader()));
//        pool.insertClassPath(new ClassClassPath(this.getClass())); // 添加当前类的类路径
        CtClass ctClass = pool.get(className);
        boolean frozen = ctClass.isFrozen();
        messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, String.format("当前文件锁定状态:[%s]", frozen));
        if (frozen) {
            ctClass.defrost();
        }
        CtMethod method = CtNewMethod.make(
                "public void hello() {" +
                        "    System.out.println(\"hello, world,Javassist\");" +
                        "}", ctClass);
        ctClass.addMethod(method);
        ctClass.setName(newFileName);
        ctClass.writeFile("target/classes/");
    }

    private void createByByteBuddy(String className) throws ClassNotFoundException, IOException {
        String newFileName = className + "Controller";
        new ByteBuddy()
                .rebase(Class.forName(className))
                .name(newFileName)
                .annotateType(
                        AnnotationDescription.Builder.ofType(RestController.class)
                                .build())
                .method(ElementMatchers.named("hello"))
                .intercept(FixedValue.value("Hello, world,ByteBuddy1"))
                .make()
                .load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.CHILD_FIRST)
                .saveIn(new File("src/main/java/"));
//                .saveIn(new File("target/classes/"));
    }

}
