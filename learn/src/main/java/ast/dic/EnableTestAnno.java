package ast.dic;

import javassist.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
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
        ClassPool pool = ClassPool.getDefault();
//        pool.appendClassPath(new LoaderClassPath(getClass().getClassLoader()));
        pool.insertClassPath(new ClassClassPath(this.getClass())); // 添加当前类的类路径
        System.err.println("start EnableTestAnno.process()");
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(CrabapplesController.class);
        for (Element element : elementsAnnotatedWith) {
            try {
                TypeElement typeElement = ((TypeElement) element);
                String className = typeElement.getQualifiedName().toString();// ast.dic.DemoController
                System.err.println(className);
                CtClass ctClass = pool.get(className);
                boolean frozen = ctClass.isFrozen();
                messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, String.format("当前文件锁定状态:[%s]", frozen));
                if (frozen) {
                    ctClass.defrost();
                }
                CtMethod method = CtNewMethod.make(
                        "public void hello() {" +
                                "    System.out.println(\"hello, world\");" +
                                "}", ctClass);
                ctClass.addMethod(method);
//                ctClass.toClass();
                // 写回修改后的字节码
                ctClass.writeFile();
                System.err.println("---------------------------end");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
