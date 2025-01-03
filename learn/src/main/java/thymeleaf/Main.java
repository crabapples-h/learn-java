package thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        // 创建模板解析器

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/"); // 模板文件路径
        templateResolver.setSuffix(".txt");       // 模板文件后缀
        templateResolver.setTemplateMode("TEXT"); // 模板模式：TEXT

        // 创建模板引擎
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        // 创建上下文并传递数据
        Context context = new Context();
        context.setVariable("name", "John Doe");
        context.setVariable("balance", "$1234.56");

        // 渲染模板
        String output = templateEngine.process("template", context);

        // 输出结果
        System.out.println(output);
        System.out.println(templateResolver.getPrefix());

    }
}
