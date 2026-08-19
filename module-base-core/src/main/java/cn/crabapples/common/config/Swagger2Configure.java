package cn.crabapples.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j / OpenAPI 3.0 配置类
 *
 * @author Mr.He
 */
@Configuration
public class Swagger2Configure {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CrabApples API 文档")
                        .description("基于 Spring Boot 的 API 文档（Knife4j）")
                        .version("1.0.0"));
    }
}
