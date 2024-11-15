package cn.crabapples.mail;

import cn.crabapples.mail.properties.ConfigProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //开启配置
@ConditionalOnClass(ConfigProperties.class)
@EnableConfigurationProperties(ConfigProperties.class) //开启使用映射实体对象
@ConditionalOnProperty(//存在对应配置信息时初始化该配置类
        prefix = "crabapples.mail",//存在配置前缀
        value = "enabled",//开启
        matchIfMissing = true//缺失检查
)
public class MailAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ConfigProperties configProperties(ConfigProperties configProperties) {
        return configProperties;
    }
}
