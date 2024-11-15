package cn.crabapples.mail.builder;


import ch.ethz.ssh2.crypto.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 建造者模式-建造器(实现类)
 *
 * @author Mr.He
 * 2020/7/13 13:29
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
public class MultipartBuilder implements Builder {
    private static final Logger logger = LoggerFactory.getLogger(MultipartBuilder.class);
    private final List<BodyPart> bodyParts;
    private final Multipart multipart;

    public MultipartBuilder() {
        bodyParts = new ArrayList<>();
        multipart = new MimeMultipart();
    }

    @Override
    public Multipart build() {
        if (bodyParts.isEmpty()) {
            throw new RuntimeException("邮件内容不能为空");
        }
        bodyParts.forEach(e -> {
            try {
                multipart.addBodyPart(e);
            } catch (MessagingException messagingException) {
                throw new RuntimeException("构建邮件消息时出现异常!", messagingException);
            }
        });
        return multipart;
    }

    /**
     * 添加邮件正文
     *
     * @param content 邮件正文
     * @return MultipartBuilder
     */
    @Override
    public MultipartBuilder content(String content) {
        try {
            logger.debug("设置邮件正文:[{}]", content);
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText(content);
            bodyParts.add(bodyPart);
            return this;
        } catch (MessagingException e) {
            throw new RuntimeException("设置邮件正文出现异常!", e);
        }
    }

    /**
     * 添加邮件附件
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return MultipartBuilder
     */
    public MultipartBuilder content(String filePath, String fileName) {
        try {
            logger.debug("设置邮件附件:[{}]", filePath);
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setDataHandler(new DataHandler(new FileDataSource(filePath)));
            bodyPart.setFileName("=?UTF-8?B?" + new String(Base64.encode(fileName.getBytes())) + "?=");
            bodyParts.add(bodyPart);
            return this;
        } catch (MessagingException e) {
            throw new RuntimeException("添加邮件附件时出现异常!", e);
        }

    }
}
