package thread.demo;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main06 {
    private static final List<Integer> nums = new ArrayList<>();
    private static final List<Integer> t1 = new ArrayList<>();
    private static final List<Integer> t2 = new ArrayList<>();

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

    private static int sum(List<Integer> temp) {
        int i = 0;
        for (Integer integer : temp) {
            i += integer;
        }
        return i;
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> func(t1), "抽奖箱1");
        Thread thread2 = new Thread(() -> func(t2), "抽奖箱2");
        thread1.start();
        thread2.start();
        while (true) {
            Thread.State t1s = thread1.getState();
            Thread.State t2s = thread2.getState();
            if (t1s == t2s && t2s == Thread.State.TERMINATED) {
                System.err.println("抽奖箱1:" + t1 + "总额:" + sum(t1));
                System.err.println("抽奖箱2:" + t2 + "总额:" + sum(t2));
                break;
            }
        }
    }

    private static void func(List<Integer> temp) {
        while (true) {
            synchronized (Main06.class) {
                if (nums.size() > 0) {
                    int index = new Random().nextInt(nums.size());
                    temp.add(nums.get(index));
//                    System.out.println(Thread.currentThread().getName() + "产生了大奖:" + nums.get(index));
                    nums.remove(index);
                } else {
                    break;
                }
            }
        }
    }
}
