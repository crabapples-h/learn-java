package ast.dic;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
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
        annotataions.add(TestAnno.class.getCanonicalName());
        return annotataions;
    }

    //支持的java版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.err.println("start EnableTestAnno.process()");
        System.err.println(annotations);
//        System.err.println(roundEnv);
        System.err.println(1234);
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(TestAnno.class);
        for (Element element : elementsAnnotatedWith) {

        }

        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
//                element.
                System.err.println(element);
                System.err.println("---------------------------");
                // 处理被注解标记的元素，生成相应的代码
                // ...
            }
        }
        return true;
    }


}
