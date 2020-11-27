package demo.mq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * TODO rabbitmq演示配置
 *
 * @author Mr.He
 * 2020/3/21 2:13
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public class RabbitmqDemoConfigure {
    private static final String HOST = "127.0.0.1";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String VIRTUAL_HOST = "crabapples";
    private static final int PORT = 35672;

    /**
     * 定义一个创建到rabbitmq服务器连接的方法
     *
     * @return 返回一个到rabbitmq连接
     * @throws IOException      网络通信可能会出现异常
     * @throws TimeoutException 网络通信可能会出现异常
     */
    public static Connection getConnection() throws TimeoutException, IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        factory.setVirtualHost(VIRTUAL_HOST);
        factory.setPort(PORT);
        return factory.newConnection();
    }
}
