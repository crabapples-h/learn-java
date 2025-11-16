package cn.crabapples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"cn.crabapples"})
@Slf4j
@EnableWebSocket
public class AiService {

    public static void main(String[] args) {
        log.info("AI服务启动中...");
        SpringApplication.run(AiService.class, args);
    }

}
