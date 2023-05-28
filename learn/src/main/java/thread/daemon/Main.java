package thread.daemon;
/**
 * TODO 多线程 当其他非守护线程执行完毕之后，守护线程会陆续结束
 *      注意，是陆续结束，而不是立即结束
 *
 * @author Mr.He
 * 2023/5/27 17:12
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        },"线程1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        },"线程2");
        t2.setDaemon(true);
        t1.start();
        t2.start();
    }
}
