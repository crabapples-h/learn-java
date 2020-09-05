package cn.crabapples.common.utils.turing;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

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
@Component
@PropertySource(value = {"classpath:application-custom.properties"})
@ConfigurationProperties(prefix = "crabapples.turing")
public class TuringConfigure {
    private String url;
    private String appKey;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
