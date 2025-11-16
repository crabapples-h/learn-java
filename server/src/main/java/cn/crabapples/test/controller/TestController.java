//package cn.crabapples.test.controller;
//
//import cn.crabapples.a.common.rabbitmq.RabbitRpcConfigure;
//import com.rabbitmq.client.Channel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageBuilder;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.Objects;
//
////@RestController
//@Slf4j
//public class TestController {
//    private final RabbitTemplate rabbitTemplate;
//
//    public TestController(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//    @RequestMapping("/send/{content}")
//    public void client(@PathVariable String content) {
//        Message message = MessageBuilder.withBody(content.getBytes(StandardCharsets.UTF_8)).build();
//        Message result = rabbitTemplate.sendAndReceive(RabbitRpcConfigure.EXCHANGE_NAME, RabbitRpcConfigure.ROUTING_KEY, message);
//        if (Objects.nonNull(result)) {
//            String sendCorrelationId = message.getMessageProperties().getCorrelationId();
//            //这里不知道为什么获取不到spring_returned_message_correlation的值
//            String correlation = (String) result.getMessageProperties().getHeaders().get("spring_returned_message_correlation");
//            System.err.println("消息响应：" + sendCorrelationId + ":" + correlation);
//            if (sendCorrelationId.equals(correlation)) {
//                System.err.println(result);
//            }
//        }
//    }
//
//    @RabbitListener(queues = RabbitRpcConfigure.QUEUE_NAME)
//    public void server(Channel channel, Message message) throws IOException {
//        String messageStr = new String(message.getBody(), StandardCharsets.UTF_8);
//        log.info("[{}]收到MQ消息:[{}]", "messageHandler", messageStr);
//        Message replyMessage = MessageBuilder.withBody(("回复消息:" + messageStr).getBytes(StandardCharsets.UTF_8)).build();
//        CorrelationData correlationData = new CorrelationData(message.getMessageProperties().getCorrelationId());
//        rabbitTemplate.sendAndReceive(RabbitRpcConfigure.EXCHANGE_NAME, RabbitRpcConfigure.ROUTING_KEY2, replyMessage, correlationData);
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//    }
//}
