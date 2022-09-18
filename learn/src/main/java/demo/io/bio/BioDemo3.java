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
