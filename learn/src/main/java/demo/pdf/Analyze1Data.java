package demo.pdf;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Analyze1Data {
    // 公司名称
    private String orgName;
    // 年度
    private String analyzeYear;
    // 总体情况
    private String situation;
    // 投资“三效”评价
    private Comment3 comment3;
    // 年度投资管理过程评估
    private String evaluate;
    // 结论举措和下一步计划
    private String nextStep;


    @Getter
    @Setter
    @ToString
    @Accessors(chain = true)
    static class Comment3 {
        // 效率
        private Comment efficiency;
        // 效果
        private Comment effect;
        // 效益
        private Comment benefit;
    }

    @Getter
    @Setter
    @ToString
    @Accessors(chain = true)
    static class Comment {
        // 总体投资执行
        private String investment;
        // 重点专业投资效率
        private String proInvestment;
    }
}
