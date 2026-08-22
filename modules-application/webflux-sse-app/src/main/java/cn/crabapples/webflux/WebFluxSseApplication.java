package cn.crabapples.webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * WebFlux SSE 演示应用（响应式栈, Netty）
 *
 * 与 socket-app（MVC + Tomcat + SseEmitter）对比展示两种 SSE 后端实现方式。
 * 端口 19097。
 */
@SpringBootApplication(scanBasePackages = "cn.crabapples")
@Slf4j
public class WebFluxSseApplication {

    public static void main(String[] args) {
        log.info("WebFlux SSE 服务启动中...");
        SpringApplication.run(WebFluxSseApplication.class, args);
        log.info("WebFlux SSE 服务启动成功");
    }
}
