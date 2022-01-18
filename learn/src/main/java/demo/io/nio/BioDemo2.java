package demo.io.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * BIO 模式基础-多线程实现
 */
public class BioDemo2 {
    private static final Logger logger = LoggerFactory.getLogger(BioDemo2.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(BioDemo2::server).start();
        Thread.sleep(1000L);
        //循环模拟多个客户端并发
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            new Thread(() -> client(finalI)).start();
        }
    }

    public static void server() {
        try {
            logger.info("---server start---");
            ServerSocket serverSocket = new ServerSocket(8888);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        InputStream inputStream = socket.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            System.err.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
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
