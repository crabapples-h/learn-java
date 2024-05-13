package ast.dic;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.LinkedHashSet;
import java.util.Set;

@AutoService(Processor.class)
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
//@SupportedAnnotationTypes("ast.dic.TestAnno")
public class EnableTestAnno extends AbstractProcessor {
    //支持的注解类型
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<>();
        annotataions.add(TestAnno.class.getCanonicalName());
        throw new Error("this is custom Error");
//        return annotataions;
    }

    //支持的java版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        System.err.println(123);
        return SourceVersion.RELEASE_8;
    }

    public synchronized void init(ProcessingEnvironment processingEnv) {
        System.err.println(processingEnv);
        System.err.println("EnableTestAnno.init()");
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.err.println(annotations);
        System.err.println(roundEnv);
        System.err.println("EnableTestAnno.process()");
        return false;
    }


}
