package cn.crabapples.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器,直接生效
 * 实现 GlobalFilter 接口
 *
 * @author Ms.He
 * 2024-11-25 15:10
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    /**
     * @param exchange 请求上下文，包含了Request，Response等信息
     * @param chain    用来把请求委托(转发)给下一个过滤器
     * @return {@code Mono<Void>} 返回标识当前过滤器业务结束
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("全局路由过滤器执行->[{}]", "CustomGlobalFilter");
        ServerHttpRequest request = exchange.getRequest();
        request.mutate().headers((e)-> e.add("origin", "gateway"));
        System.err.println(exchange);
        System.err.println(chain);
        System.err.println(request);
//            ServerHttpRequest request = request.mutate()
//                    .headers(httpHeaders -> httpHeaders.add(config.getName(), value)).build();
        return chain.filter(exchange.mutate().build());
    }


    @Override
    public int getOrder() {
        return 10;
    }
}
