package Mr.He.pattern23.singleton;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Constructor;

/**
 * TODO 单例模式测试客户端（使用反射破解单例模式）
 *
 * @author Mr.He
 * @date 12/19/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class Client02 {
    static Logger logger = LoggerFactory.getLogger(Client02.class);
    public static void main(String[] args) throws Exception {
        Singleton06 singleton06_1 = Singleton06.getInstance();
        Singleton06 singleton06_2 = Singleton06.getInstance();
        logger.info("Hello:{}",singleton06_1);
        logger.info("Hello:{}",singleton06_2);

        new Thread(()->{
            try {
                reflectDemo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                deserializationDemo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 通过反射破解单例模式
     * @throws Exception 反射异常
     */
    public static void reflectDemo() throws Exception {
        Class<Singleton06> clazz = (Class<Singleton06>) Class.forName("Mr.He.pattern23.singleton.Singleton06");
        Constructor<Singleton06> constructors = clazz.getDeclaredConstructor(null);
        constructors.setAccessible(true);
        Singleton06 singleton06_3 = constructors.newInstance();
        logger.info("Hello:{}",singleton06_3);
    }


    /**
     * 通过反序列化破解单例模式
     * @throws Exception 反序列化异常
     */
    public static void deserializationDemo() throws Exception {
        Singleton06 singleton06_1 = Singleton06.getInstance();
        logger.info("Hello:{}",singleton06_1);
        File file = new File("SingletonDemoFile.temp");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(singleton06_1);

        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Singleton06 singleton06_2 = (Singleton06) objectInputStream.readObject();
        logger.info("Hello:{}",singleton06_2);
    }
}
