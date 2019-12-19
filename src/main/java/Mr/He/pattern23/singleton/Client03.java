package Mr.He.pattern23.singleton;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.concurrent.CountDownLatch;

/**
 * TODO 单例模式测试客户端（测试五种方式在多线程环境下效率）
 *
 * @author Mr.He
 * @date 12/19/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class Client03 {
    static Logger logger = LoggerFactory.getLogger(Client03.class);

    public static void main(String[] args) throws Exception {
        singleton01Demo();
        singleton02Demo();
        singleton03Demo();
        singleton04Demo();
        singleton05Demo();
    }

    public static void singleton01Demo() throws Exception {
        long startTime = System.currentTimeMillis();
        int threadNum = 10;
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int sum = 0; sum < threadNum; sum++) {
            new Thread(() -> {
                for (int time = 0; time < 1000; time++) {
                    Singleton01 singleton01 = Singleton01.getInstance();
                }
                countDownLatch.countDown();

            }).start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        logger.info("singleton01总耗时:{}ms", endTime - startTime);
    }

    public static void singleton02Demo() throws Exception {
        long startTime = System.currentTimeMillis();
        int threadNum = 10;
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int sum = 0; sum < threadNum; sum++) {
            new Thread(() -> {
                for (int time = 0; time < 1000; time++) {
                    Singleton02 singleton02 = Singleton02.getInstance();
                }
                countDownLatch.countDown();

            }).start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        logger.info("singleton02总耗时:{}ms", endTime - startTime);
    }

    public static void singleton03Demo() throws Exception {
        long startTime = System.currentTimeMillis();
        int threadNum = 10;
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int sum = 0; sum < threadNum; sum++) {
            new Thread(() -> {
                for (int time = 0; time < 1000; time++) {
                    Singleton03 singleton03 = Singleton03.getInstance();
                }
                countDownLatch.countDown();

            }).start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        logger.info("singleton03总耗时:{}ms", endTime - startTime);
    }

    public static void singleton04Demo() throws Exception {
        long startTime = System.currentTimeMillis();
        int threadNum = 10;
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int sum = 0; sum < threadNum; sum++) {
            new Thread(() -> {
                for (int time = 0; time < 1000; time++) {
                    Singleton04 singleton04 = Singleton04.getInstance();
                }
                countDownLatch.countDown();

            }).start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        logger.info("singleton04总耗时:{}ms", endTime - startTime);
    }

    public static void singleton05Demo() throws Exception {
        long startTime = System.currentTimeMillis();
        int threadNum = 10;
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int sum = 0; sum < threadNum; sum++) {
            new Thread(() -> {
                for (int time = 0; time < 1000; time++) {
                    Singleton05 singleton05 = Singleton05.INSTANCE;
                }
                countDownLatch.countDown();

            }).start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        logger.info("singleton05总耗时:{}ms", endTime - startTime);
    }


}
