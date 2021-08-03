package cn.crabapples.common.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
//@Component
public class NoticeQueueHandler {
    String queueName = "notice";
    String exchangeName = "noticeExchange";
    String routingKey = "noticeKey";

    @RabbitListener(queues = "notice")
    @RabbitHandler
    public void messageHandler(Channel channel, Message message) throws IOException {
        System.out.println(channel);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println(new String(message.getBody(), StandardCharsets.UTF_8));

    }
}
