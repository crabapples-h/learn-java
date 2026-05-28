package demo.pdf;

import com.lowagie.text.pdf.BaseFont;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;

public class HtmlToPdfJdk8 {
    private final static List<String> TEMPLATE_MAP = new ArrayList<>();

    static {
        TEMPLATE_MAP.add("index1");
        TEMPLATE_MAP.add("index2");
        TEMPLATE_MAP.add("index3");
    }

    public static void main(String[] args) throws Exception {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("TEXT");

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);


        Context context = new Context();

        Map<String, Objects> analyze1Data = new HashMap();
        Map<String, Objects> analyze2Data = new HashMap();
        Map<String, Objects> analyze3Data = new HashMap();
        context.setVariable("analyze1Data", analyze1Data);
        context.setVariable("analyze2Data", analyze2Data);
        context.setVariable("analyze3Data", analyze3Data);


        for (String item : TEMPLATE_MAP) {
            Path newHtmlPath = Paths.get(item + "_output.html");
            Path newPdfPath = Paths.get(item + "_output.pdf");
            OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(newHtmlPath));
            engine.process(item, context, writer);


            String htmlPath = newHtmlPath.toAbsolutePath().toString();
            String output = newPdfPath.toAbsolutePath().toString();

//			createPDFv1(htmlPath, output);
//			createPDFv2(htmlPath, output);
            createPDFv3(htmlPath, output);


        }
        System.out.println("PDF生成成功");
    }

    // 使用itext生成pdf,字体名称固定,需要在html模版中声明
    static void createPDFv3(String htmlPath, String output) throws IOException {
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont(
                "blade-service/blade-effects/src/main/resources/fonts/simsun.ttf",
                BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.setDocument(htmlPath);
        renderer.layout();
        renderer.createPDF(new FileOutputStream(output));
    }

    // 使用openhtmltopdf生成pdf,字体名称自定义,需要在html模版中声明
    static void createPDFv2(String htmlPath, String output) throws IOException {
        try (FileOutputStream os = new FileOutputStream(output)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            String fontPrefix = "blade-service/blade-effects/src/main/resources/fonts/{0}";
            String font = MessageFormat.format(fontPrefix, "simsun.ttf");
            builder.useFont(new File(font), "aaa");
            builder.withFile(new File(htmlPath));
            builder.toStream(os);
            builder.run();
        }
    }

    // 使用Chrome生成pdf, 需要安装Chrome
    static void createPDFv1(String htmlPath, String output) throws IOException, InterruptedException {
        Process process = new ProcessBuilder(
                // Chrome 可执行程序（Mac路径）
                "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome",
                "--headless", // 无界面运行
                "--print-to-pdf=" + output,  // 生成 PDF
                "--no-pdf-header-footer",  // 去掉页眉页脚
                // 打开本地 HTML
                "file://" + htmlPath
        ).start();

        int code = process.waitFor();

        System.out.println("完成，退出码：" + code);
    }
}
