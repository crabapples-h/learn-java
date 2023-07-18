package thread.demo;


import java.util.ArrayList;
import java.util.List;

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
