package demo.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class QueueListener extends DefaultConsumer {
    private static final Logger logger = LoggerFactory.getLogger(QueueListener.class);
    private final String name;

    public QueueListener(Channel channel, String name) {
        super(channel);
        this.name = name;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        super.handleDelivery(consumerTag, envelope, properties, body);
        String message = new String(body, StandardCharsets.UTF_8);
        logger.info("[{}]获取到新的消息:[{}]", name, message);
    }
}
