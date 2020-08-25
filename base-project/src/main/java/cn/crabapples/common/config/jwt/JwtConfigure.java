package cn.crabapples.common.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfigure {
    private String authKey;
    private String clientId;
    private String base64Secret;
    private String name;
    private int expiresSecond;

}
