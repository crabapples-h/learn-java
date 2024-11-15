package cn.crabapples.common.dic;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * TODO 用于在编译时期插桩需要翻译的字段
 * 业务逻辑后续补充
 *
 * @author Mr.He
 * 2023/8/30 20:31
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("cn.crabapples.common.dic.EnableDict")
public class EnableDictAsp extends AbstractProcessor {
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }


}
