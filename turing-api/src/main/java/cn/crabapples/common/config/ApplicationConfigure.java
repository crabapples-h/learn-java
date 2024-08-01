package cn.crabapples.common.config;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * TODO 应用配置类
 *
 * @author Mr.He
 * 3/2/20 1:21 AM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Component
@PropertySource(value = {"classpath:application-custom.properties"})
@ConfigurationProperties(prefix= "crabapples.application")
@Getter
@Setter
public class ApplicationConfigure {
    public String aesKey;
    public String redisPrefix;
    public Long tokenCacheTime;
    public String salt;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
