package create.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 单例模式测试客户端（测试五种方式在多线程环境下效率）
 *
 * @author Mr.He
 * 12/19/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class ClientManyThread {
    static Logger logger = LoggerFactory.getLogger(ClientManyThread.class);
    private static final int objSum = 1000000;
    private static final int threadNum = 10;


    public static void main(String[] args) throws Exception {
        singleton01Demo(); // 饿汉式
        singleton02Demo(); // 懒汉式
        singleton03Demo(); // 双重检查锁模式
        singleton04Demo(); // 静态内部类模式
        singleton05Demo(); // 枚举模式
    }

    public static void singleton01Demo() throws Exception {
        long startTime = System.currentTimeMillis();
        final CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int sum = 0; sum < threadNum; sum++) {
            new Thread(() -> {
                for (int time = 0; time < objSum; time++) {
                    SingletonHunger singletonHunger = SingletonHunger.getInstance();
                }
                countDownLatch.countDown();

            }).start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        logger.info("[{}]总耗时:[{}]ms", "饿汉式", endTime - startTime);
    }

    public static void singleton02Demo() throws Exception {
        long startTime = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int sum = 0; sum < threadNum; sum++) {
            new Thread(() -> {
                for (int time = 0; time < objSum; time++) {
                    SingletonLazy singleton02 = SingletonLazy.getInstance();
                }
                countDownLatch.countDown();

            }).start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        logger.info("[{}]总耗时:[{}]ms", "懒汉式", endTime - startTime);
    }

    public static void singleton03Demo() throws Exception {
        long startTime = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int sum = 0; sum < threadNum; sum++) {
            new Thread(() -> {
                for (int time = 0; time < objSum; time++) {
                    SingletonDoubleLock singleton03 = SingletonDoubleLock.getInstance();
                }
                countDownLatch.countDown();

            }).start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        logger.info("[{}]总耗时:[{}]ms", "双重检测锁模式", endTime - startTime);
    }

    public static void singleton04Demo() throws Exception {
        long startTime = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int sum = 0; sum < threadNum; sum++) {
            new Thread(() -> {
                for (int time = 0; time < objSum; time++) {
                    SingletonInnerClass singleton04 = SingletonInnerClass.getInstance();
                }
                countDownLatch.countDown();

            }).start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        logger.info("[{}]总耗时:[{}]ms", "静态内部类模式", endTime - startTime);
    }

    public static void singleton05Demo() throws Exception {
        long startTime = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int sum = 0; sum < threadNum; sum++) {
            new Thread(() -> {
                for (int time = 0; time < objSum; time++) {
                    SingletonEnum singleton05 = SingletonEnum.INSTANCE;
                }
                countDownLatch.countDown();

            }).start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        logger.info("[{}]总耗时:[{}]ms", "枚举模式", endTime - startTime);
    }

}
