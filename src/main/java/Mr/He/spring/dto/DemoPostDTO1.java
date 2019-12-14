package Mr.He.spring.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Calendar;
import java.util.Date;

/**
 * TODO 测试参数验证的DTO
 *
 * @author Mr.He
 * @date 2019/9/19 23:46
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Getter
@Setter
public class DemoPostDTO1 {
    /**
     * 必须为空
     */
    @Null(message = "ID必须为空")
    private String id;
    /**
     * 不能为空
     */
    @NotBlank(message = "名字不能为空")
    private String name;
    /**
     * 大于等于0
     */
    @PositiveOrZero(message = "年龄有误")
    private int age;
    /**
     * 小于等于0
     */
    @NegativeOrZero(message = "存款有误")
    private int money;

    /**
     * 当前时间之后
     */
    @FutureOrPresent(message = "时间有误")
    private Date createTime;
    /**
     * 当前时间之前
     */
    @PastOrPresent(message = "时间有误")
    private Calendar updateTime;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
