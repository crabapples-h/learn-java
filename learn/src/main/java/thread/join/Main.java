package thread.join;


/**
 * TODO 多线程 插入线程/插队线程
 *      将其他线程插入到当前线程之前执行，插入的线程执行完毕之后再继续执行当前线程
 *
 * @author Mr.He
 * 2023/5/27 17:57
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
                Thread.yield();
            }
        }, "线程1");
        t1.start();
        t1.join();
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + ":" + i);
        }
    }
}
