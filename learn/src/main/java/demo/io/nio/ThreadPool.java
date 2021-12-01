package demo.io.nio;

import java.util.concurrent.*;

public class ThreadPool {
    // 线程池执行服务
    private final ExecutorService executorService;

    /**
     *
     * @param maxThreadCount 最大线程数量
     * @param taskCount 队列最大长度
     *
     * 1.当并发的任务数量大于核心线程数量时会启动新的线程，但不会超过 maxThreadCount 所定义的最大线程数量
     * 2.当并发的任务数量大于队列最大长度时，新的连接会被阻塞，等待队列中的任务被处理完成后才会进入队列被依次处理
     * 3.当同时并发的数量大于最大线程数量加上最大队列长度时会导致程序异常
     */
    public ThreadPool(int maxThreadCount, int taskCount) {
        // 创建一个同步阻塞任务队列，队列最大长度为 taskCount
        BlockingQueue<Runnable> arrayBlockingQueue = new ArrayBlockingQueue<>(taskCount);
        // 创建一个线程池执行者，核心线程为3，最大线程为maxThreadCount，线程存活时间为30秒
        executorService = new ThreadPoolExecutor(3, maxThreadCount,
                30, TimeUnit.SECONDS, arrayBlockingQueue);
    }

    void execute(Runnable runnable) {
        //执行任务
        executorService.execute(runnable);
    }

}
