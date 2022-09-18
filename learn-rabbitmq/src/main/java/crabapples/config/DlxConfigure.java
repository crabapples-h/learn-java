package crabapples.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DlxConfigure {
    public static final String QUEUE_NAME = "my_rlx_queue";
    public static final String EXCHANGE_NAME = "my_rlx_exchange";
    public static final String ROUTING_KEY = "my_rlx_routing_key";

    @Bean(name = "my_rlx_queue")
    public Queue queue() {
        /*
         * 第一个参数:队列名称
         * 第二个参数：是否持久化到硬盘
         * 第三个参数：是否具有排他性(队列是由哪个connection创建的就只能由该connection使用)
         * 第四个参数：是否自动删除(如果该队列没有消费者，是否自动删除该队列)
         */
        return new Queue(QUEUE_NAME, true, false, false);
    }


    @Bean(name = "my_rlx_exchange")
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    @Bean(name = "my_rlx_binging")
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY);
    }
}
