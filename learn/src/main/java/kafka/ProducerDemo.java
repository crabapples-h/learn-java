package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ProducerDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        // 重试次数
        props.put("retries", "0");
        // 消息通知
        props.put("acks", "all");
        // 延迟时间
        props.put("linger.ms", 1);
        // 每一批消息最大大小
        props.put("batch.size", 10);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        String topic = "test";
        Producer<String, String> producer = new KafkaProducer<>(props);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            String key = "key:" + i;
            String content = "哈哈哈:" + i;
            ProducerRecord<String, String> message = new ProducerRecord<>(topic, key, content);
            Future<RecordMetadata> send = producer.send(message);
            RecordMetadata metadata = send.get();
            System.err.println(metadata);
        }
        long end = System.currentTimeMillis();
        System.err.println(end - start);
        producer.close();
    }
}
