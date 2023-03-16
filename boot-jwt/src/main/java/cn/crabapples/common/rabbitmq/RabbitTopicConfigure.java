package cn.crabapples.common.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;

//@Configuration
public class RabbitTopicConfigure {
    public static final String QUEUE_NAME = "my_topic_queue";
    public static final String QUEUE_NAME2 = "my_topic_queue2";
    public static final String EXCHANGE_NAME = "my_topic_exchange";
    public static final String ROUTING_KEY = "my_topic_routing_key";

    @Bean(name = "my_topic_queue")
    public Queue queue() {
        /*
         * 第一个参数:队列名称
         * 第二个参数：是否持久化到硬盘
         * 第三个参数：是否具有排他性(队列是由哪个connection创建的就只能由该connection使用)
         * 第四个参数：是否自动删除(如果该队列没有消费者，是否自动删除该队列)
         */
        return new Queue(QUEUE_NAME, true, false, false);
    }

    @Bean(name = "my_topic_queue2")
    public Queue queue2() {
        /*
         * 第一个参数:队列名称
         * 第二个参数：是否持久化到硬盘
         * 第三个参数：是否具有排他性(队列是由哪个connection创建的就只能由该connection使用)
         * 第四个参数：是否自动删除(如果该队列没有消费者，是否自动删除该队列)
         */
        return new Queue(QUEUE_NAME2, true, false, false);
    }

    @Bean(name = "my_topic_exchange")
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    @Bean(name = "my_topic_binging")
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY);
    }

    @Bean(name = "my_topic_binging2")
    public Binding binding2() {
        return BindingBuilder.bind(queue2()).to(exchange()).with(ROUTING_KEY);
    }
}
