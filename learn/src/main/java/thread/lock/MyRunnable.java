package thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyRunnable implements Runnable {
    Lock lock = new ReentrantLock();
    int i = 0;

    @Override
    public void run() {
        while (i < 100) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            lock.lock();
            if (i < 100) {
                i++;
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
            lock.unlock();
        }
    }
}
