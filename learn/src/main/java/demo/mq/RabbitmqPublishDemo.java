package demo.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

/**
 * TODO rabbitmq简单队列演示
 *
 * @author Mr.He
 * 2020/11/27 10:11
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
/*
 * 名词解释:
 * connection: 连接，rabbitmq的操作是基于通道的而不是连接，一个连接可以有多个通道
 * channel: 为了节约资源，rabbitmq的所有操作都是基于通道的，可以将通道声明为publish或者consume
 * routingkey: 用来标识某个队列的key，可以是某个单词，也可以是点分的词组，如:i.love.you
 * exchange: 交换机，用于将消息(按照一定的规则)投递至指定的队列
 *      type:
 *          direct: 只有消息的routingkey和绑定的routingkey完全相同，exchange才会投递消息
 *          topic: 和direct类型差不多，但direct类型要求routingkey完全相等，这里的routingkey可以有通配符
 *                 '*','#' 其中'*'表示匹配一个单词, '#'表示多个单词
 *          fanout: 无须对消息的routingkey进行匹配,直接将消息路由到所有绑定的队列中
 *          header: 根据header来判断，其中的header是在channel.queueBind()时的一个可选参数
 *      durable: 持久化，可以简单的理解为和redis一样，如果不设置持久化，那么断电或者重启mq后消息将丢失
 *      autoDelete: 自动删除，当和这个交换机(或队列)有关的队列全都不再使用时(或消费者全部断开连接时)，自动删除当前交换机(或队列)
 *      arguments: 一些其他参数，目前暂不解释
 * queue: 队列，存储消息的地方(相同名词已在上面解释)
 *      exclusive: 私有化，为true时，则对不同connection所创建的channel不可见(对相同connection创建的channel是可见的)，反之亦然
 */
public class RabbitmqPublishDemo {
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqPublishDemo.class);
    public static void main(String[] args) throws TimeoutException, IOException, InterruptedException {
        // 获取connection
        Connection connection = RabbitmqDemoConfigure.getConnection();
        logger.info("准备将数据推送到消息服务器");
        // 获取通道(类似于NIO的操作方式，不明白的可以先去了解一下)
        Channel channel = connection.createChannel();
        // 声明一个交换机，名字为:demoExchange,类型为:fanout,是否持久化:true,是否自动删除:false,其他参数:null   (可以有多个交换机)
        channel.exchangeDeclare("demoExchange", "fanout", true, false, null);
        // 声明一个队列，名字为:demoQueue,是否持久化:true,是否是私有的:false,是否自动删除:false,其他参数:null
        channel.queueDeclare("demoQueue", true, false, false, null);
        // 将队列(demoQueue)和交换机(demoExchange)绑定，并设置routingKey为:demo
        // (二者之间是多对多的关系，即一个交换机可以绑定多个队列，一个队列可以被多个交换机绑定)
        channel.queueBind("demoQueue", "demoExchange", "demo");
        for (int i = 0; i < 50; i++) {
            // 向队列发送50条消息，每次间隔一秒
            String message = String.format("第 %d 条消息:", i);
            channel.basicPublish("demoExchange", "demo", null, message.getBytes());
            logger.info("消息发送成功:[{}]", message);
            Thread.sleep(1000);
        }
        //关闭通道
        //关闭连接
        channel.close();
        connection.close();
        logger.info("数据推送到消息服务器完成,当前时间:[{}]", LocalDateTime.now());
    }
}


