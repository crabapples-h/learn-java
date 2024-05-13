package demo.jvm;

/**
 * 内存溢出演示
 *
 * @author Mr.He
 * 2022/5/28 19:14
 * pc-name mrhe
 * -Xss108K
 */
public class Demo03 {
    static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        try {
            method01();
        } catch (Throwable e) {
            e.printStackTrace();
            System.err.println(i);
        }
    }

    static void method01() throws InterruptedException {
        i++;
//        Thread.sleep(100);
        method01();
    }
}
