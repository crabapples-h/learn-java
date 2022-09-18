package demo.jvm;
/**
 * 内存不安全演示
 * @author Mr.He
 * 2022/5/28 19:15
 * pc-name mrhe
 */
public class Demo02 {
    static Integer i = 0;

    public static void main(String[] args) {
        new Thread(new TaskThread()).start();
        new Thread(new TaskThread()).start();
        System.err.println(i);
    }

    static class TaskThread implements Runnable {
        @Override
        public void run() {
            for (int j = 0; j < 500; j++) {
                i++;
            }
            System.err.println(i);
        }
    }
}
