package cn.crabapples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;


/**
 * TODO springBoot主启动类
 *
 * @author Mr.He
 * 2019/8/5 22:53
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 *
 * EnableJpaAuditing 开启jpa审计
 * EnableNacosConfig 开启nacos配置管理 [可在配置文件中配置,且注解会覆盖配置文件] (springBoot)
 * NacosPropertySource 读取的nacos配置文件Id(springBoot)
 * EnableNacosDiscovery 开启nacos服务发现(springBoot)
 * EnableDiscoveryClient 开启服务注册发现功能(springCloud)
 * springBoot 使用application配置文件
 * springCloud 使用bootstrap配置文件
 */

@EnableJpaAuditing
@SpringBootApplication
//@Import({DynamicDataSourceRegister.class})
//@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "192.168.3.20:8848"))
//@NacosPropertySource(dataId = "learn-dev.yml", autoRefreshed = true)
public class BootJpaJwt {
    private static final Logger logger = LoggerFactory.getLogger(BootJpaJwt.class);

    public static void main(String[] args) {
        SpringApplication.run(BootJpaJwt.class, args);
        logger.info(">>>>>>>>SpringBoot服务启动成功 [nacos] [jpa] [jwt] >>>>>>>>>");
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/index.html"));
    }
//    @Bean
    public WebServerFactoryCustomizer containerCustomizer() {
        return (WebServerFactoryCustomizer<ConfigurableWebServerFactory>) factory -> {
            // 对嵌入式servlet容器的配置
            // factory.setPort(8081);
            /* 注意：new ErrorPage(stat, path);中path必须是页面名称，并且必须“/”开始。
                底层调用了String.java中如下方法：
                public boolean startsWith(String prefix) {
                    return startsWith(prefix, 0);
                }*/
            ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST,
                    "/index");
            ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND,
                    "/index");
            ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,
                    "/index");
            factory.addErrorPages(errorPage400, errorPage404,
                    errorPage500);
        };
    }
}
