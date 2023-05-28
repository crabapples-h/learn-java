package thread.syncBlock;

/**
 * TODO 多线程 同步代码块
 *      使用synchronized同步代码块保证代码块内的代码在同一时间只有一个线程可以操作
 *      注意，同步代码块的锁对象要求全局唯一
 *
 * @author Mr.He
 * 2023/5/28 15:28
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class Main {
    public static void main(String[] args) {
        Thread thread1 = new MyThread("线程1");
        Thread thread2 = new MyThread("线程2");
        Thread thread3 = new MyThread("线程3");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
