package cn.crabapples.spring;

import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.alibaba.nacos.spring.context.annotation.discovery.EnableNacosDiscovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;

/**
 * TODO springBoot主启动类
 *
 * @author Mr.He
 * 2019/8/5 22:53
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@EnableJpaAuditing
@SpringBootApplication
@NacosPropertySource(dataId = "learn-dev-yml",autoRefreshed = true)
//@EnableNacosConfig
//@EnableNacosDiscovery
public class DemoApplication {
    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        logger.info(">>>>>>>>服务启动成功>>>>>>>>>");
    }
}
