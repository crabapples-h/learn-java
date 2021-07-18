package cn.crabapples.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RabbitMqConfigure {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMqConfigure(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Bean
    public Queue notice() {
        return new Queue("notice", true);
    }

    @Bean
    public Queue demo1() {
        return new Queue("demo1", true);
    }

    public RabbitAdmin rabbitAdmin(Queue notice) {
        String exchangeName = "noticeExchange";
        String routingKey = "noticeKey";
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
//        DirectExchange exchange = ExchangeBuilder.directExchange(exchangeName).build();
        DirectExchange directExchange = new DirectExchange(exchangeName);
        rabbitAdmin.declareExchange(directExchange);
        rabbitAdmin.declareQueue(notice);
        Binding binding = BindingBuilder.bind(notice).to(directExchange).with(routingKey);
        rabbitAdmin.declareBinding(binding);
        return rabbitAdmin;
    }

}
