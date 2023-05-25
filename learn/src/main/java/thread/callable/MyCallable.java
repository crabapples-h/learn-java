package thread.callable;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws InterruptedException {
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum+=i;
        }
        Thread.sleep(3000L);
        return sum;
    }
}
