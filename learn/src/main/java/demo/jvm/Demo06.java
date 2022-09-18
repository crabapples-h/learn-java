package demo.jvm;

/**
 * 死锁
 *
 * @author Mr.He
 * 2022/5/28 21:16
 * pc-name mrhe
 */
public class Demo06 {

    public static void main(String[] args) throws InterruptedException {
        Object a = new Object();
        Object b = new Object();
        new Thread(() -> {
            synchronized (a) {
                System.out.println("线程【1】锁住了：【a】");
                try {
                   Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (b) {
                    System.out.println("线程【1】锁住了：【b】");
                }
            }
        }).start();
        Thread.sleep(1000L);
        new Thread(() -> {
            synchronized (b) {
                System.out.println("线程【2】锁住了：【b】");
                synchronized (a) {
                    System.out.println("线程【2】锁住了：【a】");
                }
            }
        }).start();
    }


}
