package pattern23.prototype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * TODO 原型模式
 *
 * @author Mr.He
 * 2020/7/14 15:57
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws Exception {
        test1();
        test2();
        test3();
    }

    /**
     * 浅克隆
     */
    static void test1() throws CloneNotSupportedException {
        Color color = new Color("red");
        Flower source = new Flower("rose", color);
        Flower target = source.clone();
        color.setColor("pink");
        logger.info("source:[{}]", source);
        logger.info("target:[{}]", target);
    }

    /**
     * 深克隆
     */
    static void test2() throws CloneNotSupportedException {
        Color color = new Color("red");
        Flower source = new Flower("rose", color);
        Flower target = source.deepClone();
        color.setColor("pink");
        logger.info("source:[{}]", source);
        logger.info("target:[{}]", target);
    }

    /**
     * 深克隆(序列化方式)
     */
    static void test3() throws IOException, ClassNotFoundException {
        Color color = new Color("red");
        Flower source = new Flower("rose", color);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(source);
        byte [] sourceByte = byteArrayOutputStream.toByteArray();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(sourceByte);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Flower target = (Flower) objectInputStream.readObject();
        color.setColor("pink");
        logger.info("source:[{}]", source);
        logger.info("target:[{}]", target);

    }
}
