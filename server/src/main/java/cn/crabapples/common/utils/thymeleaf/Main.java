package cn.crabapples.common.utils.thymeleaf;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
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
        generatorController(path, moduleName, "Controller", context);
        generatorService(path, moduleName, "Service", context);
        generatorServiceImpl(path, moduleName, "ServiceImpl", context);
        generatorMapper(path, moduleName, "Mapper", context);
        generatorDao(path, moduleName, "DAO", context);
        generatorEntity(path, moduleName, "Mapper", context);
//        for (String item : TEMPLATE_LIST) {
//            String filePath = MessageFormat.format(FILE_NAME_TEMPLATE, path, moduleName, item);
//            log.info("生成文件:[{}]", filePath);
////            File file = new File(filePath);
////            OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()));
////
////            ENGINE.process(item, context, writer);
//
//        }


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

    private static void generatorController(String path, String moduleName, String template, Context context) throws IOException {
        String filePath = MessageFormat.format(FILE_NAME_TEMPLATE, path, moduleName, template);
        log.info("生成文件:[{}]", filePath);
        File file = new File(filePath);
        OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()));
        ENGINE.process(template, context, writer);
        writer.flush();
        writer.close();
    }

    private static void generatorService(String path, String moduleName, String template, Context context) throws IOException {
        String filePath = MessageFormat.format(FILE_NAME_TEMPLATE, path, moduleName, template);
        log.info("生成文件:[{}]", filePath);
        File file = new File(filePath);
        OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()));
        ENGINE.process(template, context, writer);
        writer.flush();
        writer.close();
    }

    private static void generatorServiceImpl(String path, String moduleName, String template, Context context) throws IOException {
        String filePath = MessageFormat.format(FILE_NAME_TEMPLATE, path, moduleName, template);
        log.info("生成文件:[{}]", filePath);
        File file = new File(filePath);
        OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()));
        ENGINE.process(template, context, writer);
        writer.flush();
        writer.close();
    }

    private static void generatorMapper(String path, String moduleName, String template, Context context) throws IOException {
        String filePath = MessageFormat.format(FILE_NAME_TEMPLATE, path, moduleName, template);
        log.info("生成文件:[{}]", filePath);
        File file = new File(filePath);
        OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()));
        ENGINE.process(template, context, writer);
        writer.flush();
        writer.close();
    }
}
