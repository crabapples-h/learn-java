package cn.crabapples.webflux.sse.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebFlux 版 SSE 演示
 *
 * 与 MVC 版（ServerSentEventController，SseEmitter）对比：
 * - MVC: SseEmitter + 线程手动推送
 * - WebFlux: 返回 Flux<ServerSentEvent>，响应式流，可配 interval/backpressure
 *
 * 接口（经网关 discovery-locator /api/{serviceId}/** 路由，serviceId=webflux-sse）。
 * 注意：网关 discovery.locator.filters 为空 → 不会自动 RewritePath strip 前缀，
 * 转发会保留 /api/webflux-sse 前缀，所以 controller 必须用完整路径。
 *
 * - GET  /api/webflux-sse/sse/connect/{id}  建立 SSE 连接（返回 Flux 流）
 * - GET  /api/webflux-sse/sse/connect/timer/{id}  定时推送（Flux.interval）
 * - POST /api/webflux-sse/sse/send/{id}     触发推送
 *
 * @author Mr.He
 * 2026/8/22
 */
@RestController
@RequestMapping("/api/webflux-sse/sse")
@Slf4j
public class WebFluxSseController {

    /**
     * 每个客户端 id -> Sinks.Many（响应式事件源）
     * 用 unicast 单播：一个 id 只能被一个客户端消费
     */
    private static final Map<String, Sinks.Many<ServerSentEvent<String>>> SSE_CLIENT_MAP = new ConcurrentHashMap<>();

    @GetMapping("/connect/{id}")
    public Flux<ServerSentEvent<String>> connectSse(@PathVariable String id) {
        log.info("[webflux-sse] 收到建立连接请求, id:[{}]", id);

        // 创建或复用该 id 的 Sinks（响应式事件总线）
        Sinks.Many<ServerSentEvent<String>> sink = SSE_CLIENT_MAP.computeIfAbsent(id,
                key -> Sinks.many().unicast().onBackpressureBuffer());

        // 返回 Flux：sink 作为事件源，连接关闭时清理
        return sink.asFlux()
                .doOnCancel(() -> {
                    log.info("[webflux-sse] 连接关闭, id:[{}]", id);
                    SSE_CLIENT_MAP.remove(id);
                })
                .doOnComplete(() -> SSE_CLIENT_MAP.remove(id));
    }

    /**
     * 定时推送演示（无需先 connect，独立 Flux 周期推送）
     * 配合前端展示 interval 方式的响应式流
     */
    @GetMapping("/connect/timer/{id}")
    public Flux<ServerSentEvent<String>> connectTimer(@PathVariable String id) {
        log.info("[webflux-sse] 定时推送, id:[{}]", id);
        return Flux.interval(Duration.ofSeconds(1))
                .take(10)
                .map(i -> ServerSentEvent.<String>builder()
                        .event("log")
                        .data(String.format("[webflux] 第[%d]次消息推送", i))
                        .build())
                .doOnCancel(() -> log.info("[webflux-sse] 定时推送取消, id:[{}]", id));
    }

    /**
     * 触发向指定 id 推送 10 条消息（与 MVC 版 send 接口对应）
     */
    @PostMapping("/send/{id}")
    public void sseSend(@PathVariable String id) {
        log.info("[webflux-sse] 收到发送请求, id:[{}]", id);
        Sinks.Many<ServerSentEvent<String>> sink = SSE_CLIENT_MAP.get(id);
        if (sink == null) {
            log.warn("[webflux-sse] 未找到连接, id:[{}]", id);
            return;
        }
        for (int i = 0; i < 10; i++) {
            ServerSentEvent<String> event = ServerSentEvent.<String>builder()
                    .event("log")
                    .data(String.format("第[%d]次消息推送", i))
                    .build();
            sink.tryEmitNext(event);
        }
    }
}
