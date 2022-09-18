package crabapples.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayedConfigure {
    public static final String QUEUE_NAME = "my_delayed_queue";
    public static final String EXCHANGE_NAME = "my_delayed_exchange";
    public static final String ROUTING_KEY = "my_delayed_routing_key";

    @Bean(name = "my_delayed_queue")
    public Queue queue() {
        return new Queue(QUEUE_NAME, true, false, false);
    }


    @Bean(name = "my_delayed_exchange")
    public CustomExchange exchange() {
        Map<String, Object> args = new HashMap<>();
        //指定交换机类型
        args.put("x-delayed-type", "direct");
        return new CustomExchange(EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    @Bean(name = "my_delayed_binging")
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY).noargs();
    }
}
