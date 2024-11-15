package cn.crabapples.mail.builder;

import javax.mail.Multipart;

/**
 * TODO 建造者模式-建造器
 *
 * @author Mr.He
 * 2020/7/13 13:28
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
public interface Builder {
    Multipart build();
    Builder content(String contentText);
    Builder content(String filePath, String fileName);
}
