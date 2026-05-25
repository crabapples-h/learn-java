package demo.io.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class NioDemo2 {
    public static void main(String[] args) throws FileNotFoundException {
        try (FileOutputStream fileInputStream = new FileOutputStream("/Users/mshe/Desktop/channelTest.txt")){
            FileChannel fileChannel = fileInputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("hello".getBytes(StandardCharsets.UTF_8));
            int write = fileChannel.write(buffer);
            System.err.println(write);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
