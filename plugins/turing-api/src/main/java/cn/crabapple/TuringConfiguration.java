package cn.crabapple;

import cn.crabapple.turing.TuringApiProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration //开启配置
@ConditionalOnClass(TuringApiProperties.class)
@EnableConfigurationProperties(TuringApiProperties.class) //开启使用映射实体对象
@ConditionalOnProperty(//存在对应配置信息时初始化该配置类
        prefix = "crabapples.turing",//存在配置前缀
        value = "enabled",//开启
        matchIfMissing = true//缺失检查
)
public class TuringConfiguration {
    // 这里如果再次注入bean的话会导致存在两个Bean，spring启动时会无法识别使用哪一个bean
//    @Bean
//    @ConditionalOnMissingBean
//    public TuringApiProperties configProperties(TuringApiProperties properties) {
//        System.err.println(123);
//        return properties;
//    }
}
