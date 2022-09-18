package cn.crabapples.rabbitmq;

import cn.crabapples.common.config.rabbitmq.RabbitDirectConfigure;
import cn.crabapples.common.config.rabbitmq.RabbitFanoutConfigure;
import cn.crabapples.common.config.rabbitmq.RabbitTopicConfigure;
import cn.crabapples.common.config.rabbitmq.RabbitTtlConfigure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;

/**
 * TODO rabbitMq测试
 *
 * @author Mr.He
 * 2020/3/20 0:56aaaa
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class RabbitmqTest {
    @Autowired
    RabbitTemplate rabbitTemplate;

    //发送消息到Direct交换机中
    @Test
    public void case01() {
        rabbitTemplate.convertAndSend(RabbitDirectConfigure.EXCHANGE_NAME, RabbitDirectConfigure.ROUTING_KEY, "hello,world");
    }

    //发送消息到Fanout交换机中
    @Test
    public void case02() {
        rabbitTemplate.convertAndSend(RabbitFanoutConfigure.EXCHANGE_NAME, RabbitFanoutConfigure.ROUTING_KEY, "hello,world");
    }

    //发送消息到Topic交换机中
    @Test
    public void case03() {
        rabbitTemplate.convertAndSend(RabbitTopicConfigure.EXCHANGE_NAME, RabbitTopicConfigure.ROUTING_KEY, "hello,world");
    }

    //发送消息到TTL
    @Test
    public void case04() {
        //setExpiration方法有一个注释，有点搞笑，感兴趣可以去看看
        //具体是在 org.springframework.amqp.core.MessageProperties.setExpiration()方法上
        Message message = MessageBuilder.withBody("hello world".getBytes(StandardCharsets.UTF_8)).setExpiration("10000").build();
        rabbitTemplate.convertAndSend(RabbitTtlConfigure.EXCHANGE_NAME, RabbitTtlConfigure.ROUTING_KEY, message);
    }
}
