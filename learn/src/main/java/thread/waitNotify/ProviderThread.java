package thread.waitNotify;

public class ProviderThread extends Thread {
    @Override
    public void run() {
        while (true) {
            synchronized (Controller.lockObj) {
                if (Controller.count == 0) {
                    break;
                } else {
                    if (Controller.status == 0) {
                        System.out.println("生产者生成数据");
                        Controller.status = 1;
                        Controller.lockObj.notifyAll();
                    } else {
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
