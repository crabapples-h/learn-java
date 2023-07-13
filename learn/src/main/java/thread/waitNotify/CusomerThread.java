package thread.waitNotify;

public class CusomerThread extends Thread {
    @Override
    public void run() {
        while (Controller.count > 0) {
            // 注意：wait、notify、notifyAll方法只能只同步上下文中调用
            synchronized (Controller.lockObj) {
                if (Controller.status == 1) {
                    System.out.println("消费者消费数据：" + --Controller.count);
                    Controller.status = 0;
                    Controller.lockObj.notifyAll();
                } else {
                    System.out.println("消费者等待。。。");
                    try {
                        Controller.lockObj.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
