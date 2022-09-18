package demo.mq.base;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import demo.mq.RabbitmqDemoConfigure;
import demo.mq.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * TODO rabbitmq简单队列演示(生产者)
 *
 * @author Mr.He
 * 2022/9/9 15:12
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class Publisher {
    private static final Logger logger = LoggerFactory.getLogger(Publisher.class);

    public static void main(String[] args) throws TimeoutException, IOException, InterruptedException {
        Connection connection = RabbitmqDemoConfigure.getConnection();
        Channel channel = connection.createChannel();
        //定义一个队列
        channel.queueDeclare(Config.QUEUE_NAME, false, false, false, null);
        Sender.send2Queue(channel, Config.QUEUE_NAME);
        channel.close();
        connection.close();
        logger.info("消息发送结束");
    }
}


