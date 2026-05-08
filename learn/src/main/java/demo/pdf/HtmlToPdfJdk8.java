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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

        Analyze1Data analyze1Data = buildData1();
        Analyze2Data analyze2Data = buildData2();
        Analyze3Data analyze3Data = buildData3();
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

    private static Analyze3Data buildData3() {
        Analyze3Data analyzeData = new Analyze3Data()
                .setOrgName("测试")
                .setProjectName("测试")
                .setSituation("项目基本情况，项目建设内容和规模目标等，项目评价开展情况及主要结论。")
                .setPreliminary("决策流程是否符合企业投资管理制度要求；建设必要性；可行性研究报告、初步设计（含概算）文件等主要内容和调整情况；投资规模；项目单位及参建单位等。")
                .setProcess("前期开工准备和要素保障；建设过程、工期进度和安全生产；投资概算执行偏离度评价（包含可研批复金额偏离度、设计批复金额偏离度等）；竣工验收等")
                .setRunEnv("项目完成后的运行效果、制度建设执行情况，财务及经济效益、网络资源利用率、社会效益、资源和能源节约利用与保护效果、技术效果等评价。")
                .setProcess("问题为导向提出下一步改进举措。")
                .setTarget("对照项目批复目标的完成情况评价。")
                .setProcess("问题为导向提出下一步改进举措。")
                .setImprove("对以上评估内容进行总结提炼，得出项目后评估的主要结论。总结该项目的成功经验与存在的问题，针对项目后评估发现的主要问题，提出改进措施。");

        // 表格数据
        Analyze3Data.TableData tableData = new Analyze3Data.TableData()
                .setProjectName("项目名称")
                .setProvinceName("山东")
                .setDeptName("市场部")
                .setPriceCreate("1000000.00")
                .setPriceEnd("2000.00")
                .setScoreEnd("10")
                .setTargetExpectation("这个是预期目标的一段文字")
                .setTargetFinish("这个是实际完成的一段文字");

        LinkedList<LinkedList<String>> data = new LinkedList<>();
        for (int i = 0; i < 13; i++) {
            LinkedList<String> row = new LinkedList<>();
            row.add("10000.00");
            row.add("300.00");
            row.add("5");
            row.add("改进措施");
            data.add(row);
        }
        LinkedList<Analyze3Data.Performance> performances = Analyze3Data.Performance.init(data);

        tableData.setPerformance(performances);
        analyzeData.setTableData(tableData);
        return analyzeData;
    }

    private static Analyze2Data buildData2() {
        return new Analyze2Data()
                .setOrgName("测试")
                .setProName("测试")
                .setBackend("简要阐述该专题（业）的选题背景和具体情况。")
                .setComment3("围绕所选专题，开展多维挖掘分析，包括但不限于加强纵横对标分析，如横向与其他分公司公司、友商等对标，纵向加强历史数据的趋势分析；强化结构化分析，如拆解到最小业务单元；内外部驱动因素分析，如外部市场、行业等变化情况等，内部投资精细化管理、前后端协同等。")
                .setNextStep("问题为导向提出下一步改进举措。");
    }

    private static Analyze1Data buildData1() {
        // 效率
        Analyze1Data.Comment efficiency = new Analyze1Data.Comment()
                .setInvestment("预算执行、新建立项、新建列账、续建列账完成情况；投资结转、转固率等风险管控情况，评估实际完成与计划值差异情况，找出产生偏差的原因。")
                .setProInvestment("结合各专业“三效”投资评价指标体系，对移动网、光网、IDC、云、组网专线、ICT、承载支撑等专业的预算执行、能力建设、新续建项目、结转、转固等情况，评估实际完成与计划值差异情况，找出产生偏差的原因。");
        // 效果
        Analyze1Data.Comment effect = new Analyze1Data.Comment()
                .setInvestment("投资结构、投资方向及变化趋势情况")
                .setProInvestment("结合各专业“三效”投资评价指标体系进行分析，重点围绕各专业的网络覆盖、资源使用以及用户满意度情况。");
        // 效益
        Analyze1Data.Comment benefit = new Analyze1Data.Comment()
                .setInvestment("结合总体效益评价指标进行近几年趋势分析，查找影响投资效益的主要因素。通过与全集团平均水平、同类省公司、友商等横向对比及与自身历年情况的纵向对比，评估公司投资效益水平，判断未来趋势。")
                .setProInvestment("对直接收入相关的重点专业（如无线网、光网、IDC、云、组网专线、ICT等）的投资利润率、投资回报率、单位能力收入等经济效益指标进行分析评价。");

        Analyze1Data.Comment3 comment3 = new Analyze1Data.Comment3()
                .setEfficiency(efficiency)
                .setEffect(effect)
                .setBenefit(benefit);

        return new Analyze1Data()
                .setOrgName("测试")
                .setAnalyzeYear("2025")
                .setSituation("对公司在基础业务和战新业务市场发展、投资管理、能力实现等概况进行简要回顾。本年度投资评价工作组织开展情况。")
                // 三效评价
                .setComment3(comment3)
                .setEvaluate("评估全年本单位投资管理体系建设情况，包括但不限于健全管理机制、完善制度规范、加强过程管理、强化数字化能力等。")
                .setNextStep("总结梳理主要评估结论，提出本公司投资管理的主要经验和存在的主要问题。并针对评估结论，明确具体应用场景和预计成效，提出下一步改进举措。");
    }
}
