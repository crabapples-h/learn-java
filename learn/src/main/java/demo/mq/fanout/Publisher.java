package demo.mq.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import demo.mq.RabbitmqDemoConfigure;
import demo.mq.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * TODO rabbitmq简单扇形交换机演示(生产者)
 *
 * @author Mr.He
 * 2022/9/9 17:06
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class Publisher {
    private static final Logger logger = LoggerFactory.getLogger(Publisher.class);

    public static void main(String[] args) throws TimeoutException, IOException, InterruptedException {
        try {
            Connection connection = RabbitmqDemoConfigure.getConnection();
            Channel channel = connection.createChannel();
            //定义一个队列
            channel.queueDeclare(Config.QUEUE_NAME, true, false, false, null);
            channel.queueDeclare(Config.QUEUE_NAME2, true, false, false, null);
            //定义一个交换机
            channel.exchangeDeclare(Config.EXCHANGE_NAME, BuiltinExchangeType.FANOUT, true);
            //将队列和交换机绑定在一起
            channel.queueBind(Config.QUEUE_NAME, Config.EXCHANGE_NAME, Config.ROUTING_NAME);
            channel.queueBind(Config.QUEUE_NAME2, Config.EXCHANGE_NAME, Config.ROUTING_NAME);
            Sender.send2Exchange(channel, Config.EXCHANGE_NAME, "123");
            channel.close();
            connection.close();
            logger.info("消息发送结束");
        } catch (IOException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}


