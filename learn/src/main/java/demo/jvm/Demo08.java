package demo.jvm;

import java.util.concurrent.TimeUnit;

/**
 * 堆内存使用和回收
 *
 * @author Mr.He
 * 2022/5/29 15:00
 * pc-name mrhe
 */
public class Demo08 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("1");
        TimeUnit.SECONDS.sleep(30);
        byte[] data = new byte[1024 * 1024 * 1024];
        System.out.println("2");
        TimeUnit.SECONDS.sleep(30);
        System.gc();
        System.out.println("3");
        TimeUnit.SECONDS.sleep(30);
    }

}
