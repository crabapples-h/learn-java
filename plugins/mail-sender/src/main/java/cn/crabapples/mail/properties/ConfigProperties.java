package cn.crabapples.mail.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TODO 邮件配置类
 *
 * @author Mr.He
 * 2020/3/20 21:15
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@ConfigurationProperties(prefix = "crabapples.mail")
public class ConfigProperties {
    private String host;//邮件发送服务器地址
    private String source; //发件人邮箱
    private String port;//邮件发送服务器端口
    private String username;//发件人账号
    private String password;//发件人密码
    private boolean isAuth;//是否需要授权
    private boolean isDebug;//是否开启debug日志
    private String protocol;
    private String socketFactory;
    private String[] targets = {};//收件人邮箱

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSocketFactory() {
        return socketFactory;
    }

    public void setSocketFactory(String socketFactory) {
        this.socketFactory = socketFactory;
    }

    public String[] getTargets() {
        return targets;
    }

    public void setTargets(String[] targets) {
        this.targets = targets;
    }
}
