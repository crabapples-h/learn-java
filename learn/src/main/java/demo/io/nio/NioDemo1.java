package demo.io.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class NioDemo1 {
    public static void main(String[] args) {
        // 创建一个byte类型缓冲区，容量为10
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.err.println(buffer);
        buffer.put("haha".getBytes(StandardCharsets.UTF_8));
        System.err.println(buffer);
    }
}
