package cn.crabapples.predicate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 自定义路由断言工厂
 *
 * @author Mr.He
 * 2024-11-24 23:24
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Component
@Slf4j
public class CustomRoutePredicateFactory extends AbstractRoutePredicateFactory<CustomRoutePredicateFactory.Config> {

    // 需要在构造函数中指定配置类
    public CustomRoutePredicateFactory() {
        super(Config.class);
    }

    // 配置类中的属性，顺序为application.yml中配置的顺序，可有多个，按逗号分隔
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("name", "value");
    }

    /**
     * 断言逻辑
     *
     * @param config 配置信息
     * @return 是否通过
     */
    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return (e) -> {
            ServerHttpRequest request = e.getRequest();
            log.debug("请求详情:[{}]", request);
            return true;
        };
    }

    @Validated
    @Getter
    @Setter
    public static class Config {
        private String name;
        private String value;

    }
}
