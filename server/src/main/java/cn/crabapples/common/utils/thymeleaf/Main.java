package cn.crabapples.common.utils.thymeleaf;

import com.alibaba.fastjson2.JSONObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    private final static List<String> TEMPLATE_LIST = new ArrayList<>();
    private final static ClassLoaderTemplateResolver TEMPLATE_RESOLVER = new ClassLoaderTemplateResolver();
    private final static TemplateEngine ENGINE = new TemplateEngine();
    private final static String FILE_NAME_TEMPLATE = "{0}{1}{2}.java";

    static {
        TEMPLATE_LIST.add("Controller");
        TEMPLATE_LIST.add("Service");
        TEMPLATE_LIST.add("ServiceImpl");
        TEMPLATE_LIST.add("Entity");
        TEMPLATE_LIST.add("DAO");
        TEMPLATE_LIST.add("Mapper");
        TEMPLATE_RESOLVER.setPrefix("/templates/");
        TEMPLATE_RESOLVER.setSuffix(".cjt");
        TEMPLATE_RESOLVER.setTemplateMode("TEXT");
        ENGINE.setTemplateResolver(TEMPLATE_RESOLVER);

    }

    public static void main(String[] args) throws IOException {
        String separator = FileSystems.getDefault().getSeparator();
        String packageName = "user";
        String moduleName = "SysUser";
        String basePackage = "cn.crabapples";


        Context context = new Context();
        JSONObject field = new JSONObject();
        field.put("name", "test");
        field.put("type", "Integer");

        context.setVariable("basePackage", basePackage);
        context.setVariable("packageName", packageName);
        context.setVariable("url", "Test");
        context.setVariable("moduleName", moduleName);
        context.setVariable("fields", Collections.singletonList(field));


        String path = "server" + separator +
                "src" + separator +
                "main" + separator +
                "java" + separator +
                "cn" + separator +
                "crabapples" + separator +
                "custom" + separator +
                packageName + separator;
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        for (String item : TEMPLATE_LIST) {
            String filePath = MessageFormat.format(FILE_NAME_TEMPLATE, path, moduleName, item);
            File file = new File(filePath);
            System.err.println(file.getAbsolutePath());
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));

            ENGINE.process(item, context, writer);

        }


//        String output = templateEngine.process("Controller", context);
//        URL systemResource = ClassLoader.getSystemResource("aaa.java");
//        System.err.println(systemResource);
//        OutputStreamWriter output = new OutputStreamWriter("");
//        ENGINE.process("Controller", context,null);
//        String output = ENGINE.process("Service", context);
//        String output = ENGINE.process("ServiceImpl", context);
//        String output = ENGINE.process("Entity", context);
//        String output = ENGINE.process("DAO", context);
//        String output = ENGINE.process("Mapper", context);

//        System.out.println(output);

    }
}
