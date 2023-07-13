package thread.demo;


public class Main02 {
    private static int count = 100;

    public static void main(String[] args) {
        new Thread(() -> {
            while (count > 10) {
                func();
            }
        }, "添狗1").start();
        new Thread(() -> {
            while (count > 10) {
                func();
            }
        }, "添狗2").start();
    }

    private synchronized static void func() {
        try {
            System.out.println(Thread.currentThread().getName() + ":" + count);
            count--;
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
