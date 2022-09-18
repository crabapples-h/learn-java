import crabapples.Application;
import crabapples.config.*;
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
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * TODO rabbitMq测试
 *
 * @author Mr.He
 * 2022/9/13 20:30
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class RabbitmqTest {
    @Autowired
    RabbitTemplate rabbitTemplate;

    //发送消息到Direct交换机中
    @Test
    public void case01() {
        String content = "hello world";
        rabbitTemplate.convertAndSend(DirectConfigure.EXCHANGE_NAME, DirectConfigure.ROUTING_KEY, content);
    }

    //发送消息到Fanout交换机中
    @Test
    public void case02() {
        String content = "hello world";
        rabbitTemplate.convertAndSend(FanoutConfigure.EXCHANGE_NAME, FanoutConfigure.ROUTING_KEY, content);
    }

    //发送消息到Topic交换机中
    @Test
    public void case03() {
        String content = "hello world";
        rabbitTemplate.convertAndSend(TopicConfigure.EXCHANGE_NAME, TopicConfigure.ROUTING_KEY, content);
    }

    //发送消息时设置TTL过期时间
    @Test
    public void case04() {
        //setExpiration方法有一个注释，有点搞笑，感兴趣可以去看看
        //具体是在 org.springframework.amqp.core.MessageProperties.setExpiration()方法上
        String content = "hello world";
        Message message = MessageBuilder.withBody(content.getBytes(StandardCharsets.UTF_8)).setExpiration("10000").build();
        rabbitTemplate.convertAndSend(TtlConfigure.EXCHANGE_NAME, TtlConfigure.ROUTING_KEY, message);
    }

    //发送消息到设置TTL过期时间的队列中
    @Test
    public void case05() {
        String content = "hello world";
        Message message = MessageBuilder.withBody(content.getBytes(StandardCharsets.UTF_8)).build();
        rabbitTemplate.convertAndSend(TtlConfigure.EXCHANGE_NAME, TtlConfigure.ROUTING_KEY, message);
    }

    //发送消息到延迟队列中(使用插件的方式)
    @Test
    public void case06() throws InterruptedException {
        String content = "hello world-" + LocalDateTime.now();
        Message message = MessageBuilder.withBody(content.getBytes(StandardCharsets.UTF_8)).setHeader("x-delay", 1000 * 10).build();
        rabbitTemplate.convertAndSend(DelayedConfigure.EXCHANGE_NAME, DelayedConfigure.ROUTING_KEY, message);
        TimeUnit.SECONDS.sleep(15);
    }
}
