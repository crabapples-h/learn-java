package thread.demo;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * TODO 多线程-案例5
 *  有一个抽奖池,该抽奖池中存放了奖励的金额,该抽奖池中的奖项为10,5,20,50,100,200,500,800,2,80,300,7003
 *  创建两个抽奖箱(线程)设置线程名称分别为“抽奖箱1”“抽奖箱2'随机从抽奖池中获取奖项元素并打印在控制台上,格式如下
 *  每次抽出一个奖项就打印一个(随机)
 *  抽奖箱1 又产生了一个 10 元大奖
 *  抽奖箱1 又产生了一个 100 元大奖
 *  抽奖箱1 又产生了一个 200 元大奖
 *  抽奖箱1 又产生了一个 800 元大奖
 *  抽奖箱2 又产生了一个 700 元大奖
 *  ....
 *
 * @author Ms.He
 * 2023/8/2 14:44
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
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
                } else {
                    break;
                }
            }
        }
    }
}
