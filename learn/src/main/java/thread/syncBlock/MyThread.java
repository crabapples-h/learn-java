package thread.syncBlock;

import org.jetbrains.annotations.NotNull;

public class MyThread extends Thread {
    public MyThread(@NotNull String name) {
        super(name);
    }

    // 使用static修饰变量，保证该变量全局唯一，使其他线程也能对该对象进行修改
    static int i = 0;
    // 同步代码块锁对象(锁对象，使用static修饰，保证对象的实例唯一)
    static Object o = new Object();

    @Override
    public void run() {
        while (i < 100) {
            // 同步代码块 也可以使用当前类的字节码对象 MyThread.class
            synchronized (o) {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (i < 100) {
                    i++;
                    System.out.println(getName() + ":" + i);
                }
            }
        }
    }
}
