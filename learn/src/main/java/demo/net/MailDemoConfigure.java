package demo.net;

import com.alibaba.fastjson2.JSONObject;

/**
 * TODO 邮件配置类
 *
 * @author Mr.He
 * 2020/3/20 21:15
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public class MailDemoConfigure {
    private String host = "smtp.qq.com";
    private String source = "xxx@qq.com";
    private String port = "465";
    private String username = "xxx@qq.com";
    private String password = "password";
    ;
    private boolean isAuth = true;
    private boolean isDebug = true;
    private String protocol = "smtp";
    private String socketFactory = "javax.net.ssl.SSLSocketFactory";
    private String[] targets = {"xxxxxx@qq.com"};

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public String getHost() {
        return this.host;
    }

    public String getSource() {
        return this.source;
    }

    public String getPort() {
        return this.port;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isAuth() {
        return this.isAuth;
    }

    public boolean isDebug() {
        return this.isDebug;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getSocketFactory() {
        return this.socketFactory;
    }

    public String[] getTargets() {
        return this.targets;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuth(boolean isAuth) {
        this.isAuth = isAuth;
    }

    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setSocketFactory(String socketFactory) {
        this.socketFactory = socketFactory;
    }

    public void setTargets(String[] targets) {
        this.targets = targets;
    }
}
