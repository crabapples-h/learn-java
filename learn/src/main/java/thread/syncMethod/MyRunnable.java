package thread.syncMethod;

public class MyRunnable implements Runnable {

    int i = 0;

    @Override
    public void run() {
        while (i < 100) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            add();
        }
    }

    // 同步方法，方法为非静态方法，锁对象为this;当同步方法为静态方法时，锁对象为当前类的字节码对象(MyRunnable.class)
    synchronized void add() {
        if (i < 100) {
            i++;
            System.out.println(Thread.currentThread().getName() + ":" + i);
        }
    }
}
