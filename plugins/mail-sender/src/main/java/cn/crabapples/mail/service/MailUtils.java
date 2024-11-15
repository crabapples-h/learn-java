package cn.crabapples.mail.service;

import cn.crabapples.mail.properties.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * TODO SMTP邮件工具类
 *
 * @author Mr.He
 * 2021/8/9 12:34
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name admin
 */
@Component
public class MailUtils {
    private static final Logger logger = LoggerFactory.getLogger(MailUtils.class);
    private final ConfigProperties configProperties;
    private final Session session;

    public MailUtils(ConfigProperties configProperties) {
        this.configProperties = configProperties;
        session = initSession();
    }

    /**
     * 初始化session
     *
     * @return session
     */
    private Session initSession() {
        logger.info("开始初始化session");
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", configProperties.getProtocol());
        properties.put("mail.smtp.host", configProperties.getHost());
        properties.put("mail.smtp.auth", configProperties.isAuth());
        properties.put("mail.smtp.port", configProperties.getPort());
        properties.put("mail.smtp.socketFactory.class", configProperties.getSocketFactory());
        Session session = Session.getDefaultInstance(properties);
        session.setDebug(configProperties.isDebug());
        logger.info("初始化session完成:[{}]", session);
        return session;
    }

    /**
     * 初始化邮件数据
     *
     * @param title   邮件标题
     * @param targets 收件人
     * @return 邮件数据
     */
    private MimeMessage initMessage(String title, String[] targets) {
        try {
            logger.info("开始初始化message");
            MimeMessage message = new MimeMessage(session);
            message.setSubject(title);
            message.setFrom(new InternetAddress(configProperties.getSource()));
            logger.debug("发件人设置为:[{}]", configProperties.getSource());
            /*
             * ↑ ↑ ↑ ↑ ↑ ↑ ↑
             * 设置发件人地址
             *
             * 设置收件人地址
             * ↓ ↓ ↓ ↓ ↓ ↓ ↓
             */
            InternetAddress[] target = new InternetAddress[targets.length];
            for (int i = 0; i < targets.length; i++) {
                logger.debug("添加收件人:[{}]", targets[i]);
                target[i] = new InternetAddress(targets[i]);
            }
            message.addRecipients(Message.RecipientType.TO, target);
            logger.info("初始化message完成:[{}]", message);

            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("邮件初始化出现异常", e);
        }
    }

    /**
     * 发送邮件
     *
     * @param multipart 邮件内容
     * @param title     邮件标题
     * @param targets   收件人
     * @return 发送结果
     */
    public boolean sendEmail(Multipart multipart, String title, String[] targets) {
        try {
            logger.info("开始初始化邮件内容");
            MimeMessage message = initMessage(title, targets);
            message.setContent(multipart);
            message.saveChanges();
            logger.info("开始发送邮件");
            Transport transport = session.getTransport(configProperties.getProtocol());
            transport.connect(configProperties.getHost(), configProperties.getUsername(), configProperties.getPassword());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            logger.info("邮件发送完成");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("邮件发送失败", e);
        }
    }
}
