package cn.crabapples.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义路由过滤器
 * 继承 AbstractGatewayFilterFactory 类
 *
 * @author Mr.He
 * 2024-11-25 15:10
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Component
public class CustomGatewayFilterFactory extends AbstractGatewayFilterFactory<CustomGatewayFilterFactory.Config> {
    public CustomGatewayFilterFactory() {
        super(Config.class);
    }

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
                ServerHttpRequest sourceRequest = exchange.getRequest();
                System.err.println(config);
                System.err.println(exchange);
                System.err.println(chain);
                System.err.println(sourceRequest);
//            ServerHttpRequest request = sourceRequest.mutate()
//                    .headers(httpHeaders -> httpHeaders.add(config.getName(), value)).build();
                return chain.filter(exchange.mutate().build());
            }
        };
    }


    @Validated
    @Getter
    @Setter
    public static class Config {
        private String name;
        private String value;
        private String test;
        private String test2;

    }

}
