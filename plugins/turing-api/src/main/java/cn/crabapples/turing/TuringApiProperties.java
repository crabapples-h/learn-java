package cn.crabapples.turing;

import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TODO 图灵机器人API配置
 *
 * @author Mr.He
 * 9/5/20 3:01 PM
 * e-mail hequan@gogpay.cn
 * qq 294046317
 * pc-name root
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "crabapples.turing")
public class TuringApiProperties {
    private String url; //图灵机器人url
    private String appKey;//图灵机器人appkey

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
