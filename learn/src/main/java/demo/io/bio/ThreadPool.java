package demo.io.bio;

import java.util.concurrent.*;


/**
 * 线程池
 */
public class ThreadPool {
    private static final int CORE_THREAD_COUNT = 5;
    private static final int THREAD_KEEP_ALIVE_TIME = 30;
    // 线程池任务执行者
    private final ExecutorService executorService;

    /**
     * 1.当[并发的任务数量]大于[核心线程数量]时会启动新的线程，但不会超过 MAX_THREAD_COUNT 所定义的最大线程数量
     * 2.当[并发的任务数量]大于[最大线程数量]时，新的连接会被放入队列中，等待其他线程处理完成后才会进入从队列中被取出执行
     * 3.当[并发的数量]大于[最大线程数量]加上[最大队列长度]时会导致程序异常
     *
     * @param maxThreadCount 最大线程数量
     * @param taskCount      队列最大长度
     */
    public ThreadPool(int maxThreadCount, int taskCount) {
        // 创建一个同步阻塞任务队列，队列最大长度为 taskCount
        BlockingQueue<Runnable> arrayBlockingQueue = new ArrayBlockingQueue<>(taskCount);
        // 创建一个线程池执行者，核心线程数为3，最大线程数为maxThreadCount，线程存活时间为30秒
        executorService = new ThreadPoolExecutor(CORE_THREAD_COUNT, maxThreadCount,
                THREAD_KEEP_ALIVE_TIME, TimeUnit.SECONDS, arrayBlockingQueue);
    }

    /**
     * 执行任务
     */
    void execute(Runnable runnable) {
        //执行任务
        executorService.execute(runnable);
    }
}
