package cn.crabapples.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootConfiguration
//@EnableAutoConfiguration
//@ComponentScan(basePackages = {"cn.crabapples"})
@SpringBootApplication
@Slf4j
public class AiApplication {

    public static void main(String[] args) {
        // springboot2.x版本无法启动,后续更换为3.x版本使用webflex
        log.info("AI服务启动中...");
        SpringApplication.run(AiApplication.class, args);
    }

}
