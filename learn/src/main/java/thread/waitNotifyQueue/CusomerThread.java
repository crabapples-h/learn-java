package thread.waitNotifyQueue;

import java.util.concurrent.ArrayBlockingQueue;

public class CusomerThread extends Thread {
    private final ArrayBlockingQueue<String> queue;

    public CusomerThread(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            try {
                // take方法底层使用了lock对象，所以这里不用写在同步代码块内
                String poll = queue.take();
                // 打印语句没有在同步代码块里所以有时候会连续打印。但实际业务并不受影响
                System.out.println("消费者消费数据：" + poll);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
