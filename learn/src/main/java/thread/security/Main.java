package thread.security;

/**
 * TODO 多线程 线程安全问题
 *      多个线程同时操作一个静态资源时，由于线程执行顺序的不确定性，会导致结果不确定
 *
 * @author Mr.He
 * 2023/5/28 15:23
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
