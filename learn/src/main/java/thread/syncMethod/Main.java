package thread.syncMethod;

/**
 * 多线程 同步方法
 * 使用synchronized同步方法保证synchronized修饰的方法在同一时间只有一个线程可以操作
 * 注意，同步方法是锁住方法里的所有代码
 * synchronized同步方法是静态方法时，锁对象为当前类的字节码对象；是非静态方法时，锁对象为this
 *
 * @author Ms.He
 * 2023/5/28 17:06
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class Main {
    public static void main(String[] args) {
        final MyRunnable myRunnable = new MyRunnable();
        Thread thread1 = new Thread(myRunnable, "线程1");
        Thread thread2 = new Thread(myRunnable, "线程2");
        Thread thread3 = new Thread(myRunnable, "线程3");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
