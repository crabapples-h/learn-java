package crabapples.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
//@Component
public class TtlHandler {
    @RabbitListener(queues = TtlConfigure.QUEUE_NAME)
    public void messageHandler(Channel channel, Message message) throws IOException {
        String messageStr = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("收到MQ消息,监听器:[{}],队列:[{}],内容:[{}]", "messageHandler", TtlConfigure.QUEUE_NAME, messageStr);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
