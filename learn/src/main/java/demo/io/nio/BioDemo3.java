package demo.io.nio;

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
     static class ThreadPool {
        // 线程池任务执行者
        private final ExecutorService executorService;
        /**
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
        for (int i = 0; i < 25; i++) {
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
            ThreadPool threadPool = new ThreadPool(20, 4);
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
                            System.out.println(Thread.currentThread().getName()+":"+ line);
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
            System.out.println("任务number:" + i);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
            printWriter.println("任务number:" + i);
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("---client close---");
        }
    }
}
