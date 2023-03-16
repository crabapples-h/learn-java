package demo.jvm;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * 使用Jvisualvm工具查看堆内存对象信息
 *
 * @author Mr.He
 * 2022/5/30 21:22
 * pc-name mrhe
 */
public class Demo09 {
    static class Love {
        byte[] data = new byte[1024 * 1024];
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Love> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add(new Love());
        }
        TimeUnit.HOURS.sleep(1);
    }

}
