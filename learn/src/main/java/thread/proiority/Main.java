package thread.proiority;

/**
 * TODO 多线程-线程优先级(priority)
 *      优先级越高，获得cpu执行时间的概率就越高
 *      最小优先级：1， 最大优先级：10， 默认优先级：5
 *      仅仅只是概率更高，并不一定能够优先执行
 *
 * @author Mr.He
 * 2023/5/27 17:07
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        });

        t1.setPriority(10);
        t2.setPriority(1);
        t1.start();
        t2.start();
    }
}
