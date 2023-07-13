package thread.waitNotifyQueue;

import java.util.concurrent.ArrayBlockingQueue;

public class ProviderThread extends Thread {
    private final ArrayBlockingQueue<String> queue;

    public ProviderThread(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // take方法底层使用了lock对象，所以这里不用写在同步代码块内
                queue.put("hello");
                // 打印语句没有在同步代码块里所以有时候会连续打印。但实际业务并不受影响
                System.out.println("生产者生产数据");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
