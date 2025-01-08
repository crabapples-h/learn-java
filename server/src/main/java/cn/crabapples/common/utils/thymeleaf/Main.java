package cn.crabapples.common.utils.thymeleaf;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.*;

@Slf4j
public class Main {
    private final static Map<String, String> TEMPLATE_MAP = new HashMap<>();
    private final static String CLASS_NAME_TEMPLATE = "{0}/{1}.java";
    private final static String XML_NAME_TEMPLATE = "{0}/{1}.xml";
    private final static String FILE_PATH_TEMPLATE = "{0}{1}";


    static {
        TEMPLATE_MAP.put("$$Controller", "controller");
        TEMPLATE_MAP.put("$$Service", "service");
        TEMPLATE_MAP.put("$$ServiceImpl", "service/impl");
        TEMPLATE_MAP.put("$$", "entity");
        TEMPLATE_MAP.put("$$DAO", "dao");
        TEMPLATE_MAP.put("$$Mapper", "dao/mybatis/mapper");


    }

    public static void main(String[] args) throws IOException {
        // 包名
        String packageName = "user";
        // 类名
        String moduleName = "SysUser";
        // 基础包名
        String basePackage = "cn.crabapples";
        // api地址
        String url = "/api/test1";
        // 属性列表
        JSONObject field = new JSONObject();
        field.put("name", "String");
        field.put("type", "Integer");
        List<JSONObject> fields = Collections.singletonList(field);
        Context context = new Context();
        context.setVariable("basePackage", basePackage);
        context.setVariable("packageName", packageName);
        context.setVariable("url", url);
        context.setVariable("moduleName", moduleName);
        context.setVariable("fields", fields);


        generatorJavaClass(context, packageName, moduleName);
        generatorMybatisMapper(context, packageName, moduleName);

    }

    private static void generatorMybatisMapper(Context context, String packageName, String moduleName) throws IOException {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".cxt");
        resolver.setTemplateMode("TEXT");

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        String separator = FileSystems.getDefault().getSeparator();
        String path = "server" + separator +
                "src" + separator +
                "main" + separator +
                "java" + separator +
                "cn" + separator +
                "crabapples" + separator +
                "custom" + separator +
                packageName + separator;
        String templateName = "$$Mapper";
        String subPackageName = "dao/mybatis/xml";
        String filePath = MessageFormat.format(FILE_PATH_TEMPLATE, path, subPackageName);
        File directory = new File(filePath);
        directory.mkdirs();
        String fileName = MessageFormat.format(XML_NAME_TEMPLATE, filePath, templateName.replace("$$", moduleName));
        File file = new File(fileName);
        log.info("生成文件:[{}]", fileName);
        OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()));
        engine.process(templateName, context, writer);
    }

    private static void generatorJavaClass(Context context, String packageName, String moduleName) throws IOException {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".cjt");
        resolver.setTemplateMode("TEXT");

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        String separator = FileSystems.getDefault().getSeparator();
        String path = "server" + separator +
                "src" + separator +
                "main" + separator +
                "java" + separator +
                "cn" + separator +
                "crabapples" + separator +
                "custom" + separator +
                packageName + separator;
        for (Map.Entry<String, String> item : TEMPLATE_MAP.entrySet()) {
            String templateName = item.getKey();
            String subPackageName = item.getValue();
            String filePath = MessageFormat.format(FILE_PATH_TEMPLATE, path, subPackageName);
            File directory = new File(filePath);
            directory.mkdirs();
            String fileName = MessageFormat.format(CLASS_NAME_TEMPLATE, filePath, templateName.replace("$$", moduleName));
            File file = new File(fileName);
            log.info("生成文件:[{}]", fileName);
            OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()));
            engine.process(templateName, context, writer);
        }
    }

}
