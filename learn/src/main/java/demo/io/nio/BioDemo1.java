package demo.io.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO 模式基础工作机制
 */
public class BioDemo1 {
    private static final Logger logger = LoggerFactory.getLogger(BioDemo1.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(BioDemo1::server).start();
        Thread.sleep(1000L);
        new Thread(BioDemo1::client).start();
    }

    public static void server() {
        try {
            logger.info("---server start---");
            // 开启端口监听
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            // 读取socket连接传输的数据并打印输出
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            logger.info("---server close---");
        }
    }

    public static void client() {
        try {
            logger.info("---client start---");
            Socket socket = new Socket("localhost", 8888);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
            printWriter.println("你好，世界！");
            printWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            logger.info("---client close---");
        }
    }
}
