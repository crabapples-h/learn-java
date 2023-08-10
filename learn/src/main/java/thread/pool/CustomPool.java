package thread.pool;


import java.util.concurrent.*;

/**
 * TODO 多线程-自定义线程池
 *
 * @author Ms.He
 * 2023/8/2 14:37
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class CustomPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*
         * 1. int corePoolSize：核心线程数量 不能小于0
         * 2. int maximumPoolSize：最大线程数量 大于等于核心线程数量
         * 3. long keepAliveTime：线程空闲时间 (除核心线程外其他线程超过这个时间即会被销毁) 不能小于0
         * 4. TimeUnit unit：线程空闲时间单位 用TimeUnit指定
         * 5. BlockingQueue<Runnable> workQueue：工作队列 (阻塞队列) 不能为null
         * 6. ThreadFactory threadFactory：创建线程工厂 (创建线程) 不能为null
         * 7. RejectedExecutionHandler handler：拒绝服务(要执行的任务过多时的解决方案) 不能为null
         */
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                5,
                10,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(20),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

}
