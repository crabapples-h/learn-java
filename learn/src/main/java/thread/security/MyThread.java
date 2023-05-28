package thread.security;

import org.jetbrains.annotations.NotNull;

public class MyThread extends Thread {
    public MyThread(@NotNull String name) {
        super(name);
    }

    // 使用static修饰变量，保证该变量全局唯一，使其他线程也能对该对象进行修改
    static int i = 0;

    @Override
    public void run() {
        while (i < 100) {
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
