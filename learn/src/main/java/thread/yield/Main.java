package thread.yield;


/**
 * TODO 多线程 让出线程/礼让线程
 *      让出当前线程抢占到的cpu执行时间，开始下一次抢占
 *      这个方法可以尽可能的使多个线程的执行时间趋于平均，但仅仅只是尽可能，无法保证一定平均
 *
 * @author Mr.He
 * 2023/5/27 17:55
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
                Thread.yield();
            }
        }, "线程1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
                Thread.yield();
            }
        }, "线程2");
        t1.start();
        t2.start();
    }
}
