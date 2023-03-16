package demo.io.bio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO 模式基础-文件传输
 */
public class BioDemo5Client {
    private static final Logger logger = LoggerFactory.getLogger(BioDemo5Client.class);
    private static final int MAX_THREAD_COUNT = 30;
    private static final int QUEUE_LENGTH = 4;


    public static void main(String[] args) {
        Frame frame = new Frame("登陆");
        Panel panel = new Panel();
        Box box = Box.createVerticalBox();
        TextField ip = new TextField();
//        Font font = new Font("");
        TextField username = new TextField();
        box.add(ip);
        box.add(username);
        panel.add(box);
        frame.add(panel);
        frame.setVisible(true);
        frame.pack();

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
                        DataInputStream dataInputStream = new DataInputStream(inputStream);
                        // 休眠3秒，模拟任务处理需要花费3秒
                        Thread.sleep(3000L);
                        // 读取socket连接传输的数据并打印输出
                        String fileName = dataInputStream.readUTF();
                        System.out.println(fileName);
                        byte[] data = new byte[1024];
                        int length;
                        FileOutputStream fileOutputStream = new FileOutputStream("/Users/mrhe/Desktop/img1.png");
                        while ((length = dataInputStream.read(data)) > 0) {
                            fileOutputStream.write(data, 0, length);
                        }
                        fileOutputStream.flush();
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

    public static void client() {
        try {
            logger.info("---client start---");
            Socket socket = new Socket("localhost", 8888);
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF("喜欢你");
            FileInputStream fileInputStream = new FileInputStream("/Users/mrhe/Desktop/image_03.png");
            byte[] data = new byte[1024];
            for (int i = 0; i != -1; i = fileInputStream.read(data)) {
                dataOutputStream.write(data, 0, i);
            }
            dataOutputStream.flush();
//            socket.shutdownOutput();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("---client close---");
        }
    }
}
