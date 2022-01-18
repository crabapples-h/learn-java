package demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIODemo {
    private Selector selector;

    public void init() throws IOException {
        // 创建一个选择器
        this.selector = Selector.open();
        // 使用通道计划，服务器
        ServerSocketChannel channel = ServerSocketChannel.open();
        // 这个通道非常高效，所以要非阻塞
        channel.configureBlocking(false);
        ServerSocket serverSocket = channel.socket();
        InetSocketAddress address = new InetSocketAddress(8080);
        serverSocket.bind(address);
        System.out.println("NIO抢票神器已经成功启动，端口8080");
        channel.register(selector, SelectionKey.OP_ACCEPT);// 注册事件
    }

    public void start() throws IOException {
        // 24小时营业，无限循环
        while (true) {
            // 选择器运行
            this.selector.select();
            Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            // 抢票软件对每一个用户负责，帮大家处理事情
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();// 移除，防止重复操作
                if (key.isAcceptable()) {// 这个是填写抢票的请求
                    accept(key);
                } else if (key.isReadable()) {// 这个是抢票的操作--狂差12306
                    read(key);
                }
            }
        }

    }

    private void accept(SelectionKey key) throws IOException {
        // 事件传过来的key
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        // 转成客户端的通道
        SocketChannel channel = server.accept();
        // 设置成非阻塞
        channel.configureBlocking(false);
        // 注册一个读事件，用作去12306抢票
        channel.register(this.selector, SelectionKey.OP_READ);// 注册事件
    }

    // 12306的抢票事件
    private void read(SelectionKey key) throws IOException {
        // 创建一个缓冲区，大家一起上咯，效率高
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel channel = (SocketChannel) key.channel();
        // 在通道中读取大家的请求
        channel.read(buffer);
        String request = new String(buffer.array()).trim();// trim方法用于删除字符串两边的空格字符串
        System.out.println("抢票客户的请求" + request);
        String outString = "HTTP/1.1 200 OK\n" + "Content-Type: text/html;charset=UTF-8\n\n" + "NIOsuccess";
        ByteBuffer outBuffer = ByteBuffer.wrap(outString.getBytes());
        // 把我们的数据返回给通道（抢到票了）
        channel.write(outBuffer);
        channel.close();
    }

    public static void main(String[] args) throws Exception {
        NIODemo server = new NIODemo();
        server.init();
        server.start();
    }

}
