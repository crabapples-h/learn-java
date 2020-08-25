package cn.crabapples.common.utils.turing;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author hequan@gogpay.cn
 * @date $ $
 */
@Getter
@Setter
@Component
@PropertySource(value = {"classpath:application-custom.yml"})
@ConfigurationProperties(prefix = "turing")
public class TuringConfigure {
    private String url;
    private String appKey;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
