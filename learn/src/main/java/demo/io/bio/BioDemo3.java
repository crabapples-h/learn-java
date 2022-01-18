package demo.io.bio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * BIO 模式基础-线程池实现
 */
public class BioDemo3 {
    private static final Logger logger = LoggerFactory.getLogger(BioDemo3.class);
    private static final int MAX_THREAD_COUNT = 30;
    private static final int QUEUE_LENGTH = 4;
    private static final int CORE_THREAD_COUNT = 5;
    private static final int THREAD_KEEP_ALIVE_TIME = 30;

    /**
     * 线程池
     */
    static class ThreadPool {
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

    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(BioDemo3::server).start();
        Thread.sleep(1000L);
        for (int i = 0; i < 34; i++) {
            int finalI = i;
//            Thread.sleep(3000L);
            new Thread(() -> client(finalI)).start();
        }
    }

    public static void server() {
        try {
            logger.info("---server start---");
            ServerSocket serverSocket = new ServerSocket(8888);
            // 创建一个线程池对象
            ThreadPool threadPool = new ThreadPool(MAX_THREAD_COUNT, QUEUE_LENGTH);
            while (true) {
                Socket socket = serverSocket.accept();
                // 创建一个任务
                Runnable runnable = () -> {
                    try {
                        InputStream inputStream = socket.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        // 休眠3秒，模拟任务处理需要花费3秒
                        Thread.sleep(3000L);
                        // 读取socket连接传输的数据并打印输出
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            System.out.println(Thread.currentThread().getName() + ":" + line);
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                };
                // 使用线程池执行任务
                threadPool.execute(runnable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            logger.info("---server close---");
        }
    }

    public static void client(int i) {
        try {
            logger.info("---client start---");
            Socket socket = new Socket("localhost", 8888);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
            printWriter.println("Task Number:" + i);
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("---client close---");
        }
    }
}
