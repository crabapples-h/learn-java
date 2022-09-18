package demo.mq.base;

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
 * TODO rabbitmq简单队列演示(消费者)
 *
 * @author Mr.He
 * 2020/3/20 2:02
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public class Consume {
    private static final Logger logger = LoggerFactory.getLogger(Consume.class);

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitmqDemoConfigure.getConnection();
        Channel channel = connection.createChannel();
        DefaultConsumer consumer = new QueueListener(channel,"Consume");
        channel.basicConsume(Config.QUEUE_NAME, true, consumer);
        logger.error("线程:[{}]结束", Thread.currentThread());
    }
}
