package thread.demo;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
