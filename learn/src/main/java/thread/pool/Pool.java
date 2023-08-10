package thread.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO 多线程-线程池
 *
 * @author Ms.He
 * 2023/8/2 14:37
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class Pool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Runnable runnable = () -> System.out.println(Thread.currentThread().getName() + ":" + "hello");
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        ExecutorService workStealingPool = Executors.newWorkStealingPool();
        for (int i = 0; i < 100; i++) {
//            cachedThreadPool.submit(runnable);
//            fixedThreadPool.submit(runnable);
//            singleThreadExecutor.submit(runnable);
            workStealingPool.submit(runnable);
        }
    }

}
