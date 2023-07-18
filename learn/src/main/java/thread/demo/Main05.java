package thread.demo;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main05 {
    private static final List<Integer> nums = new ArrayList<>();

    static {
        nums.add(10);
        nums.add(5);
        nums.add(20);
        nums.add(50);
        nums.add(100);
        nums.add(200);
        nums.add(500);
        nums.add(800);
        nums.add(2);
        nums.add(80);
        nums.add(300);
        nums.add(700);
    }

    public static void main(String[] args) {
        new Thread(Main05::func, "抽奖箱1").start();
        new Thread(Main05::func, "抽奖箱2").start();
    }

    private static void func() {
        while (true) {
            synchronized (Main05.class) {
                if (nums.size() > 0) {
                    int index = new Random().nextInt(nums.size());
                    System.out.println(Thread.currentThread().getName() + "产生了大奖:" + nums.get(index));
                    nums.remove(index);
                }else {
                    break;
                }
            }
        }
    }
}
