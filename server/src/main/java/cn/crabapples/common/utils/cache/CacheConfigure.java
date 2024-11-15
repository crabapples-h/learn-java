package cn.crabapples.common.utils.cache;

import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * TODO 缓存配置类
 *
 * @author Mr.He
 * 2021/6/25 15:49
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name admin
 */
@Component
@PropertySource(value = {"classpath:application-custom.properties"})
@ConfigurationProperties(prefix = "crabapples.cache")
@Getter
@Setter
public class CacheConfigure {
    private long time;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
