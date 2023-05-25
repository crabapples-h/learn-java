package thread.runnable;

/**
 * TODO 多线程-实现Runnable接口
 *
 * @author Mr.He
 * 2023/5/26 0:28
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class Main {
    public static void main(String[] args) {
         MyRunnable myRunnable = new MyRunnable();
        Thread thread1 = new Thread(myRunnable);
        Thread thread2 = new Thread(myRunnable);
         thread1.start();
         thread2.start();
    }
}
