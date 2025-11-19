package cn.crabapples.filter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义过滤器,需要在yaml中配置才能生效
 * 执行顺序 defaultFilter(默认过滤器) > 路由过滤器 > GlobalFilter(全局过滤器)
 * 继承 AbstractGatewayFilterFactory 类
 *
 * @author Mr.He
 * 2024-11-25 15:10
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
    public CustomFilter() {
        super(Config.class);
    }

    @Validated
    @Data
    public static class Config {
        private String name;
        private String value;
        private String test;
        private String test2;
    }

    /**
     * 配置类中的属性，顺序为application.yml中配置的顺序，可有多个，按逗号分隔
     *
     * @return 属性名称
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("name", "value", "test", "test2");
    }

    /**
     * @param config 配置
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            /**
             * @param exchange 请求上下文，包含了Request，Response等信息
             * @param chain    用来把请求委托(转发)给下一个过滤器
             * @return {@code Mono<Void>} 返回标识当前过滤器业务结束
             */
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                log.info("自定义路由过滤器执行->[{}]", "CustomFilterFactory");
                ServerHttpRequest request = exchange.getRequest();
                ServerHttpResponse response = exchange.getResponse();
                System.err.println(config);
                System.err.println(exchange);
                System.err.println(chain);
                System.err.println(request);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                return response.setComplete(); // 中断请求
//            ServerHttpRequest request = request.mutate()
//                    .headers(httpHeaders -> httpHeaders.add(config.getName(), value)).build();
                return chain.filter(exchange.mutate().build());
            }
        };
    }


}
