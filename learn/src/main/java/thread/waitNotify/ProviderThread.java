package thread.waitNotify;

public class ProviderThread extends Thread {
    @Override
    public void run() {
        while (true) {
            // 注意：wait、notify、notifyAll方法只能只同步上下文中调用
            synchronized (Controller.lockObj) {
                if (Controller.count == 0) {
                    break;
                } else {
                    if (Controller.status == 0) {
                        System.out.println("生产者生成数据");
                        Controller.status = 1;
                        Controller.lockObj.notifyAll();
                    } else {
                        System.out.println("生产者等待。。。");
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
}
