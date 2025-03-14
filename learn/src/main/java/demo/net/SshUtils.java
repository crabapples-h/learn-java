package demo.net;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author cairong@gogpay.cn
 * @date 2019/4/25 17:24
 */
public class SshUtils {
    private final static Logger logger = LoggerFactory.getLogger(SshUtils.class);

    class DestHost {
        private String username;
        private String password;
        private String host;
        private Integer port;
        private Integer timeout;

        public String getUsername() {
            return this.username;
        }

        public String getPassword() {
            return this.password;
        }

        public String getHost() {
            return this.host;
        }

        public Integer getPort() {
            return this.port;
        }

        public Integer getTimeout() {
            return this.timeout;
        }
    }

    public static Session getSession(DestHost destHost) throws JSchException {

        JSch jsch = new JSch();

        Session session = jsch.getSession(destHost.getUsername(), destHost.getHost(), destHost.getPort());
        session.setPassword(destHost.getPassword());
        // 第一次访问服务器不用输入yes
        session.setConfig("StrictHostKeyChecking", "no");
        session.setTimeout(destHost.getTimeout());
        session.connect();

        return session;
    }

    public static InputStream execCommand(Session session, List<String> commandList) throws IOException, JSchException {

        // 2.尝试解决 远程ssh只能执行一句命令的情况
        ChannelShell channelShell = (ChannelShell) session.openChannel("shell");
        // 从远端到达的数据 都能从这个流读取到
        InputStream inputStream = channelShell.getInputStream();
        channelShell.setPty(true);
        channelShell.connect();

        // 写入该流的数据 都将发送到远程端
        OutputStream outputStream = channelShell.getOutputStream();
        // 使用PrintWriter 就是为了使用println 这个方法
        // 好处就是不需要每次手动给字符加\n
        PrintWriter printWriter = new PrintWriter(outputStream);
        for (String command : commandList) {
            printWriter.println(command);
            // 把缓冲区的数据强行输出
            printWriter.flush();
        }

        printWriter.close();
        outputStream.close();

        return inputStream;
    }

    public static void release(Session session) {
        session.disconnect();
    }
}
