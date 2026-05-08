package demo.pdf;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Analyze2Data {
    // 公司名称
    private String orgName;
    // 专业名称
    private String proName;
    // 背景情况
    private String backend;
    // “三效”分析及存在问题
    private String comment3;
    // 结论举措和下一步计划
    private String nextStep;
}
