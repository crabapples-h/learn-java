package demo.mq;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * TODO rabbitmq消费者演示
 *
 * @author Mr.He
 * 2020/11/27 10:12
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class RabbitmqConsumeDemo {
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqConsumeDemo.class);
    public static void main(String[] args) {
        try {
            //打开连接
            Connection connection = RabbitmqDemoConfigure.getConnection();
            //打开通道
            Channel channel = connection.createChannel();
            //定义一个消费者持续监听队列(API内部应该是启动了一个新的线程)
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                //重写消费的方法，其中最后一个参数就是接受到的消息
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    logger.info("获取到新的消息:[{}]", new String(body, StandardCharsets.UTF_8));
                }
            };
            //声明通道为Consume，autoAck自动应答为true，关于autoACK机制建议设置为false，但这里为了演示就先设置为true
            channel.basicConsume("demoQueue", true, consumer);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
