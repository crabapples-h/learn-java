package cn.crabapples.common.utils.mail;


import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * TODO 邮件配置类
 *
 * @author Mr.He
 * 2020/3/20 21:15
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Getter
@Setter
@Configuration
@PropertySource(value = {"classpath:application-custom.properties"})
@ConfigurationProperties(prefix = "crabapples.mail")
public class MailConfigure {
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

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
