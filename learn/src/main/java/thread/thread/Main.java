package thread.thread;
/**
 * TODO 多线程-继承Thread类
 *
 * @author Mr.He
 * 2023/5/26 0:28
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class Main {
    public static void main(String[] args) {
         MyThread thread1 = new MyThread();
         MyThread thread2 = new MyThread();
         thread1.start();
         thread2.start();
    }
}
