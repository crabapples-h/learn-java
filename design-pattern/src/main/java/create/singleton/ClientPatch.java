package create.singleton;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Constructor;

/**
 * 单例模式测试客户端（使用反射破解单例模式）
 *
 * @author Mr.He
 * 12/19/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class ClientPatch {
    static Logger logger = LoggerFactory.getLogger(ClientPatch.class);

    public static void main(String[] args) throws Exception {
        SingletonLazySecure instance01 = SingletonLazySecure.getInstance();
        SingletonLazySecure instance02 = SingletonLazySecure.getInstance();
        logger.info("Hello:{}", instance01);
        logger.info("Hello:{}", instance02);
        reflectDemo();
//        deserializationDemo();
//        new Thread(() -> {
//            try {
//                reflectDemo();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();
//
//        new Thread(() -> {
//            try {
//                deserializationDemo();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();
    }

    /**
     * 通过反射破解单例模式
     *
     * @throws Exception 反射异常
     */
    public static void reflectDemo() throws Exception {
        Class<SingletonLazySecure> clazz = (Class<SingletonLazySecure>) Class.forName("pattern23.singleton.Singleton06");
        Constructor<SingletonLazySecure> constructors = clazz.getDeclaredConstructor();
        constructors.setAccessible(true);
        SingletonLazySecure singleton06_3 = constructors.newInstance();
        logger.info("Hello:{}", singleton06_3);
    }


    /**
     * 通过反序列化破解单例模式
     *
     * @throws Exception 反序列化异常
     */
    public static void deserializationDemo() throws Exception {
        SingletonLazySecure singleton06_1 = SingletonLazySecure.getInstance();
        logger.info("Hello:{}", singleton06_1);
        File file = new File("SingletonDemoFile.temp");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(singleton06_1);

        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        SingletonLazySecure singleton06_2 = (SingletonLazySecure) objectInputStream.readObject();
        logger.info("Hello:{}", singleton06_2);
        SingletonLazySecure singleton06_3 = SingletonLazySecure.getInstance();
        logger.info("Hello:{}", singleton06_3);
    }
}
