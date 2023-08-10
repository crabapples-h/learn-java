package thread.demo;


import java.util.ArrayList;
import java.util.List;

/**
 * TODO 多线程-案例3
 *  同时开启两个线程，共同获取1-100之间的所有数字
 *  要求:将输出所有的奇数
 *
 * @author Ms.He
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class Main03 {
    private static int num = 1;
    private static List<Integer> nums = new ArrayList<>();

    public static void main(String[] args) {
        new Thread(() -> {
            while (num < 100) {
                func();
            }
        }, "线程1").start();
        new Thread(() -> {
            while (num < 100) {
                func();
            }
        }, "线程2").start();
        System.err.println(nums);
    }

    private synchronized static void func() {
        if (num % 2 == 1) {
            nums.add(num);
        }
        num++;
    }
}
