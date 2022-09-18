package crabapples.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class DirectHandler {

    @RabbitListener(queues = DirectConfigure.QUEUE_NAME)
    public void messageHandler(Channel channel, Message message) throws IOException {
        String messageStr = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("收到MQ消息,监听器:[{}],队列:[{}],内容:[{}]", "messageHandler", DirectConfigure.QUEUE_NAME, messageStr);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * concurrency 指并发数量，将开启n个子线程去消费消息
     */
//    @RabbitListener(queues = RabbitDirectConfigure.QUEUE_NAME2,concurrency = "20")
    public void messageHandler2(Channel channel, Message message) throws IOException {
        String messageStr = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("收到MQ消息,监听器:[{}],队列:[{}],内容:[{}]", "messageHandler2", DirectConfigure.QUEUE_NAME2, messageStr);
//        //消费成功 参数:消息的唯一标识,是否并发处理
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        //消费失败 参数:消息的唯一标识,是否并发处理,是否将消息重新放回消息队列中
//        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
//        //拒绝消费 参数:消息的唯一标识,是否将消息重新放回消息队列中
//        channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
    }
}
