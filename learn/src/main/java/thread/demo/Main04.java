package thread.demo;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * TODO 多线程-案例4
 *  抢红包也用到了多线程。假设:100块，分成了3个包，现在有5个人去抢其中，红包是共享数据5个人是5条线程
 *  打印结果如下:
 *  XXX抢到了XXX元
 *  XXX抢到了XXX元
 *  XXX抢到了XXX元
 *  XXX没抢到
 *  XXX没抢到
 *
 * @author Ms.He
 * 2023/8/2 14:43
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class Main04 {
    private static BigDecimal count = new BigDecimal("10");
    private static int successCount = 0;
    private static BigDecimal ZERO = new BigDecimal("0");

    public static void main(String[] args) {
        new Thread(Main04::func, "线程1").start();
        new Thread(Main04::func, "线程2").start();
        new Thread(Main04::func, "线程3").start();
        new Thread(Main04::func, "线程4").start();
        new Thread(Main04::func, "线程5").start();
    }

    private synchronized static void func() {
        if (successCount < 3) {
            if (successCount == 2) {
                successCount++;
                System.out.println(Thread.currentThread().getName() + "抢到了:" + count);
            } else {
                int random = (int) (Math.random() * 1000);
                BigDecimal money = new BigDecimal(random).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
                BigDecimal subtract = count.subtract(money);
                if (count.compareTo(ZERO) > 0 && subtract.compareTo(ZERO) > 0) {
                    count = subtract;
                    successCount++;
                    System.out.println(Thread.currentThread().getName() + "抢到了:" + money);
                } else {
                    func();
                }
            }
        } else {
            System.out.println(Thread.currentThread().getName() + "没抢到");
        }
    }
}
