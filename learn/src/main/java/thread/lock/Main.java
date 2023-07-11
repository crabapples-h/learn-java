package thread.lock;

/**
 * 多线程 lock对象
 * 使用lock锁对象可以手动的控制锁的获取与释放
 * 注意，lock是一个接口，需要使用它的实现类，且lock对象在多线程环境中也必须唯一
 *
 * @author Ms.He
 * 2023/5/28 17:25
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class Main {
    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        new Thread(myRunnable, "线程1").start();
        new Thread(myRunnable, "线程2").start();
        new Thread(myRunnable, "线程3").start();
    }
}
