package cn.crabapples.common.config.rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
//@Component
public class QueueHandlerDirect {

    @RabbitListener(queues = RabbitDirectConfigure.QUEUE_NAME)
    @RabbitHandler
    public void messageHandler(Channel channel, Message message) throws IOException {
        String messageStr = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("[{}]收到MQ消息:[{}]", "messageHandler", messageStr);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * concurrency 指并发数量，将开启n个子线程去消费消息
     */
    @RabbitListener(queues = RabbitDirectConfigure.QUEUE_NAME,concurrency = "20")
    @RabbitHandler
    public void messageHandler2(Channel channel, Message message) throws IOException {
        String messageStr = new String(message.getBody(), StandardCharsets.UTF_8);
        log.warn("[{}]收到MQ消息:[{}]", "messageHandler2", messageStr);
        //消费成功 参数:消息的唯一标识,是否并发处理
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        //消费失败 参数:消息的唯一标识,是否并发处理,是否将消息重新放回消息队列中
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
        //拒绝消费 参数:消息的唯一标识,是否将消息重新放回消息队列中
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
    }
}
