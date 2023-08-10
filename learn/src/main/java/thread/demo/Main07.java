package thread.demo;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * TODO 多线程-案例7 在上一题基础上继续完成如下需求
 *  在此次抽奖过程中，抽奖箱1总共产生了6个奖项，
 *  分别为:10,20,100,500,2,300 最高奖项为300元，总计额为932元
 *  在此次抽奖过程中，抽奖箱2总共产生了6个奖项，
 *  分别为:5,50,200,800,80,700 最高奖项为800元，总计额为1835元
 *  在此次抽奖过程中，抽奖箱2中产生了最大奖项,该奖项金额为800元
 *
 * @author Ms.He
 * 2023/8/2 14:45
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class Main07 {
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

    private static Integer max(List<Integer> temp) {
        return temp.stream().max(Comparator.comparingInt(a -> a)).orElse(-1);

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
                Integer t1Max = max(t1);
                Integer t2Max = max(t2);
                System.err.println("抽奖箱1:" + t1 + "总额:" + sum(t1) + "最大:" + max(t1));
                System.err.println("抽奖箱2:" + t2 + "总额:" + sum(t2) + "最大:" + max(t2));
                break;
            }
        }
    }

    private static void func(List<Integer> temp) {
        while (true) {
            synchronized (Main07.class) {
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
