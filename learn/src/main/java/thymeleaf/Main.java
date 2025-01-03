package thymeleaf;

import com.alibaba.fastjson2.JSONObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".cjt");
        templateResolver.setTemplateMode("TEXT");

        TemplateEngine templateEngine = new TemplateEngine();

        templateEngine.setTemplateResolver(templateResolver);
        Context context = new Context();
        JSONObject field = new JSONObject();
        field.put("name", "test");
        field.put("type", "Integer");

        context.setVariable("packageName", "test");
        context.setVariable("url", "Test");
        context.setVariable("moduleName", "Test");
        context.setVariable("fields", Collections.singletonList(field));

        // 渲染模板
//        String output = templateEngine.process("Controller", context);
//        String output = templateEngine.process("Service", context);
//        String output = templateEngine.process("ServiceImpl", context);
//        String output = templateEngine.process("Entity", context);
//        String output = templateEngine.process("DAO", context);
        String output = templateEngine.process("Mapper", context);

        // 输出结果
        System.out.println(output);

    }
}
