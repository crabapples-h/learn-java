package create.prototype;

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
//        test2();
//        test3();
    }

    /**
     * 浅克隆
     */
    static void test1() throws CloneNotSupportedException {
        Color color = new Color("red");
        FlowerDeepClone source = new FlowerDeepClone("rose", color);
        FlowerDeepClone target = source.clone();
        color.setColor("pink");
        logger.info("原对象:[{}],hashCode:[{}]", source, source.hashCode());
        logger.info("新对象:[{}],hashCode:[{}]", target, target.hashCode());
    }

    /**
     * 深克隆
     */
    static void test2() throws CloneNotSupportedException {
        Color color = new Color("red");
        FlowerDeepClone source = new FlowerDeepClone("rose", color);
        FlowerDeepClone target = source.deepClone();
        color.setColor("pink");
        logger.info("原对象:[{}],hashCode:[{}]", source, source.hashCode());
        logger.info("新对象:[{}],hashCode:[{}]", target, target.hashCode());
    }

    /**
     * 深克隆(序列化方式)
     */
    static void test3() throws IOException, ClassNotFoundException {
        Color color = new Color("red");
        FlowerDeepClone source = new FlowerDeepClone("rose", color);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(source);
        byte[] sourceByte = byteArrayOutputStream.toByteArray();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(sourceByte);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        FlowerDeepClone target = (FlowerDeepClone) objectInputStream.readObject();
        color.setColor("pink");
        logger.info("原对象:[{}],hashCode:[{}]", source, source.hashCode());
        logger.info("新对象:[{}],hashCode:[{}]", target, target.hashCode());
    }
}
