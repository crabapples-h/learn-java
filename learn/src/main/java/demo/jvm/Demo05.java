package demo.jvm;

import java.util.concurrent.TimeUnit;

/**
 * cpu占用过高
 *
 * @author Mr.He
 * 2022/5/28 21:06
 * pc-name mrhe
 */
public class Demo05 {

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(999);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(999);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            while (true) {
            }
        }).start();
    }


}
