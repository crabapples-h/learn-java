package demo.io.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class NioDemo2 {
    public static void main(String[] args) throws FileNotFoundException {
        try (RandomAccessFile accessFile = new RandomAccessFile("/Users/mshe/Desktop/channelTest.txt","rw")){
            FileChannel fileChannel = accessFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("hello".getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            fileChannel.write(buffer);
            buffer.clear();
            buffer.position(0) ;
            int read = fileChannel.read(buffer);
            System.err.println(new String(buffer.array()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
