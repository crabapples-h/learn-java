package thread.waitNotifyQueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * TODO 多线程 等待唤醒机制(阻塞队列实现)
 *
 * @author Ms.He
 * 2023/7/13 14:07
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class Main {
    public static void main(String[] args) {
        ArrayBlockingQueue<String> objects = new ArrayBlockingQueue<>(1);
        new ProviderThread(objects).start();
        new CusomerThread(objects).start();
    }
}
