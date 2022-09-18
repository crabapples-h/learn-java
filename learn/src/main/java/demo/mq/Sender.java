package demo.mq;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Sender {
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);

    public static void send2Queue(Channel channel,String queueName) throws IOException, InterruptedException {
        for (int i = 0; i < 50; i++) {
            String message = String.format("第 %d 条消息", i);
            channel.basicPublish("", queueName, null, message.getBytes());
            logger.info("消息发送成功:[{}]", message);
            Thread.sleep(200);
        }
    }
    public static void send2Exchange(Channel channel,String exchange,String routing) throws IOException, InterruptedException {
        for (int i = 0; i < 50; i++) {
            String message = String.format("第 %d 条消息", i);
            channel.basicPublish(exchange,routing, null, message.getBytes());
            logger.info("消息发送成功:[{}]", message);
            Thread.sleep(200);
        }
    }
}
