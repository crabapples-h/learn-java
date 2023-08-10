package thread.demo;

/**
 * TODO 多线程-案例2
 *  有100份礼品,两人同时发送，当剩下的礼品小于10份的时候则不再送出
 *  要求:利用多线程模拟该过程并将线程的名字和礼物的剩余数量打印出来
 *
 * @author Ms.He
 * 2023/8/2 14:40
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
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
