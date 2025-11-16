package cn.crabapples.socket;

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
public class WebSocketService {

    public static void main(String[] args) {
        log.info("WebSocket服务启动中...");
        SpringApplication.run(WebSocketService.class, args);
    }

}
