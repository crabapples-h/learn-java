package thread.demo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO 多线程-案例1
 *  一共有1000张电影票,可以在两个窗口领取,假设每次领取的时间为3000毫秒要求
 *  要求:用多线程模拟卖票过程并打印剩余电影票的数量
 *
 * @author Ms.He
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class Main01 {
    private static int count = 1000;
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(() -> {
            while (count > 0) {
                func();
            }
        }, "窗口1").start();
        new Thread(() -> {
            while (count > 0) {
                func();
            }
        }, "窗口2").start();
    }

    private static void func() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ":" + count);
            count--;
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
