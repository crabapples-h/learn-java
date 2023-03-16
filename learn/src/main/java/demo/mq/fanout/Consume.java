package demo.mq.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import demo.mq.QueueListener;
import demo.mq.RabbitmqDemoConfigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * TODO rabbitmq简单扇形交换机演示(消费者)
 *
 * @author Mr.He
 * 2022/9/9 17:06
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class Consume {
    private static final Logger logger = LoggerFactory.getLogger(Consume.class);

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitmqDemoConfigure.getConnection();
        Channel channel = connection.createChannel();
        DefaultConsumer consumer01 = new QueueListener(channel, "Consume-01");
        channel.basicConsume(Config.QUEUE_NAME, true, consumer01);
        DefaultConsumer consumer02 = new QueueListener(channel, "Consume-02");
        channel.basicConsume(Config.QUEUE_NAME2, true, consumer02);
    }
}
