package thread.demo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
