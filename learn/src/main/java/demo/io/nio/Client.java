package demo.io.nio;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < 25; i++) {
            int finalI = i;
            Thread.sleep(3000L);
            new Thread(() -> {
                try {
                    send(finalI);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    static void send(int i) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 8888);
        System.out.println("任务number:" + i);
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        printWriter.println("任务number:" + i);
        printWriter.flush();
        printWriter.close();
    }
}
