package demo.io.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDemo2 {
    private static final Logger logger = LoggerFactory.getLogger(ServerDemo2.class);

    public static void main(String[] args) throws IOException {
        System.err.println("---server start---");
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

    }
}
