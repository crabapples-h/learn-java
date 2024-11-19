package cn.crabapples;

import cn.crabapples.common.datasource.dynamicaop.DynamicDataSourceRegister;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocket;


/**
 * TODO springBoot主启动类
 *
 * @author Mr.He
 * 2019/8/5 22:53
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 * <p>
 * EnableJpaAuditing 开启jpa审计
 * EnableNacosConfig 开启nacos配置管理 [可在配置文件中配置,且注解会覆盖配置文件] (springBoot)
 * NacosPropertySource 读取的nacos配置文件Id(springBoot)
 * EnableNacosDiscovery 开启nacos服务发现(springBoot)
 * EnableDiscoveryClient 开启服务注册发现功能(springCloud)
 * springBoot 使用application配置文件
 * springCloud 使用bootstrap配置文件
 */

//@SpringBootApplication
@SpringBootConfiguration
@EnableAutoConfiguration
@EnableAspectJAutoProxy
@EnableWebSocket
//--------start-------
// 动态数据源，使用@DataSourceChange(name="xxx")切换
//@Import({DynamicDataSourceRegister.class})
//--------end-------

//mybatis扫描路径(如果使用注解的方式可不用配置)
@MapperScans({
//        @MapperScan("cn.crabapples.custom.dao"),
        @MapperScan("cn.crabapples.system.*.dao.mybatis.mapper"),
        @MapperScan("cn.crabapples.custom.*.dao.mybatis.mapper")

})

@ComponentScan(basePackages = {"cn.crabapples"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {DynamicDataSourceRegister.class}),
})
//@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "192.168.3.20:8848"))
//@NacosPropertySource(dataId = "learn-dev.yml", autoRefreshed = true)
//@PropertySources({
//        @PropertySource(value = "classpath:jdbc.properties", ignoreResourceNotFound = true),
//        @PropertySource(value = "common.properties", ignoreResourceNotFound = true)
//})
public class SystemApplication {
    private static final Logger logger = LoggerFactory.getLogger(SystemApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
        logger.info(">>>>>>>>SpringBoot服务启动成功 [jwt] >>>>>>>>>");
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

//    @Bean
//    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
//        return factory -> factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/index.html"));
//    }

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
