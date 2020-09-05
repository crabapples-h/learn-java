package cn.crabapples.common.utils.jwt;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * TODO jwt配置类
 *
 * @author Mr.He
 * 9/5/20 2:55 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Getter
@Setter
@Component
@PropertySource(value = {"classpath:application-custom.properties"})
@ConfigurationProperties(prefix = "crabapples.jwt")
public class JwtConfigure {
    private String authKey;
    private String clientId;
    private String base64Secret;
    private String name;
    private int expiresSecond;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
