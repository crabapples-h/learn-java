package cn.crabapples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootConfiguration
//@EnableAutoConfiguration
//@ComponentScan(basePackages = {"cn.crabapples"})
@SpringBootApplication
@Slf4j
public class AiService {

    public static void main(String[] args) {
        // springboot2.x版本无法启动,后续更换为3.x版本使用webflex
        log.info("AI服务启动中...");
        SpringApplication.run(AiService.class, args);
    }

}
