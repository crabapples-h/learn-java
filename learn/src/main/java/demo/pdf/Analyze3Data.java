package demo.pdf;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Analyze3Data {
    // 公司名称
    private String orgName;
    // 项目名称
    private String projectName;
    // 总体情况
    private String situation;
    // 项目前期决策情况
    private String preliminary;
    // 项目实施过程总结
    private String process;
    // 项目运行效果及效益评价
    private String runEnv;
    // 项目目标及可持续性评价
    private String target;
    // 项目评价结论及改进建议
    private String improve;
    // 项目运行效果及效益评价
    private TableData tableData;


    @Getter
    @Setter
    @ToString
    @Accessors(chain = true)
    static class TableData {
        // 项目名称
        private String projectName;
        // 省名称
        private String provinceName;
        // 部门名称
        private String deptName;
        // 立项批复金额
        private String priceCreate;
        // 最终完成列账金额
        private String priceEnd;
        // 最终完成列账金额 得分
        private String scoreEnd;
        // 预期目标
        private String targetExpectation;
        // 实际完成情况
        private String targetFinish;
        // 绩效目标
        private LinkedList<Performance> performance;
    }

    @Getter
    @Setter
    @ToString
    static class Performance {
        static LinkedHashMap<String, String> performanceMap = new LinkedHashMap<>();
        static {
            performanceMap.put("投资方向是否符合集团战略要求", "投资方向");
            performanceMap.put("是否按规定履行决策和审批程序擅自投资", "投资程序");
            performanceMap.put("项目建议书/可研报告批复流程是否合规", "投资程序");
            performanceMap.put("完成项目可研时间", "投资程序");
            performanceMap.put("完成项目开工时间", "投资程序");
            performanceMap.put("完成项目建设交付时间", "投资程序");
            performanceMap.put("项目用途是否与批复一致", "投资风险");
            performanceMap.put("投资规模", "投资风险");
            performanceMap.put("建设资源(可补充具体指标XX)", "投资风险");
            performanceMap.put("造价标准(可补充具体指标XX)", "投资风险");
            performanceMap.put("承诺静态回收期", "投资回报");
            performanceMap.put("承诺内部收益率", "投资回报");
            performanceMap.put("总分", "总分");
        }

        // 一级指标
        private String firstPer;
        // 二级指标
        private String secondPer;
        // 指标预期
        private String perExpectation;
        // 实际完成值
        private String finishValue;
        // 分值
        private String scoreAll;
        // 得分
        private String score;
        // 偏差分析及改进措施
        private String analysis;

        /**
         * 初始化
         *
         * @param data 表格数据,数据必须有序,且固定为12
         *             外层每个list 对应一个指标
         *             内层list 第一项是指标预期，第二项是实际完成值，第三项是得分，第四项是偏差分析及改进措施
         * @return 链式结构
         */
        public static LinkedList<Analyze3Data.Performance> init(@NotNull LinkedList<LinkedList<String>> data) {
            if (data.size() != performanceMap.size()) {
                throw new RuntimeException("数据长度不一致");
            }
            LinkedList<Performance> collect = performanceMap.entrySet().stream().map(entry -> {
                String key = entry.getKey();
                String value = entry.getValue();
                return new Performance()
                        .setFirstPer(value)
                        .setSecondPer(key);
            }).collect(Collectors.toCollection(LinkedList::new));
            for (int i = 0; i < data.size(); i++) {
                Performance performance = collect.get(i);
                performance
                        .setPerExpectation(data.get(i).get(0))
                        .setFinishValue(data.get(i).get(1))
                        .setScore(data.get(i).get(2))
                        .setAnalysis(data.get(i).get(3));
            }
            return collect;
        }
    }


}
