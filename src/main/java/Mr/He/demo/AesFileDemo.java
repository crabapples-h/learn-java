package Mr.He.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.SecureRandom;

/**
 * TODO AES加密文件演示
 *
 * @author Mr.He
  * 2019/8/5 21:37
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */

public class AesFileDemo {
    private static final Logger logger = LoggerFactory.getLogger(AesFileDemo.class);

    /**
     * 用于生成密钥的种子
     */
    private static final String KEY = "123456";

    /**
     * 用于将密钥种子转换为KEY
     * @param seed 密钥种子
     * @return 密钥
     * @throws Exception 生成密钥可能出现的异常
     */
    private static Key createKey(String seed) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128, new SecureRandom(seed.getBytes()));
        return keyGenerator.generateKey();
    }

    /**
     *
     * @param keyString 密钥
     * @param sourceFile 需要加/解密的文件
     * @param targetPath 文件输出路径
     * @param type 需要执行的操作(加/解密)
     * @return 输出的文件
     * @throws Exception 运行过程中可能出现的异常
     */
    public static String doFinal(String keyString, File sourceFile, String targetPath, int type) throws Exception {
        /*
         * 初始化加密方式
         */
        Cipher cipher = Cipher.getInstance("AES");
        File path = new File(targetPath);
        File targetFile = new File(targetPath + "/" + sourceFile.getName());
        /*
         * 判断输出路径是否存在
         */
        if(!path.exists()){
            path.mkdir();
        }
        /*
         * 判断输出文件是否存在
         */
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }
        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
        byte[] data = new byte[1024];
        /*
         * 当操作类型为加密时
         */
        if (type == Cipher.ENCRYPT_MODE) {
            /*
             * 初始化Cipher为加密
             */
            cipher.init(Cipher.ENCRYPT_MODE, createKey(keyString));
            /*
             * 创建加密流读入文件
             */
            CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
            for (int i = 0; i != -1; i = cipherInputStream.read(data)) {
                fileOutputStream.write(data, 0, i);
            }
            cipherInputStream.close();
        } else if (type == Cipher.DECRYPT_MODE) {
            /*
             * 初始化Cipher为解密
             */
            cipher.init(Cipher.DECRYPT_MODE, createKey(keyString));
            /*
             * 创建解密流输出文件
             */
            CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, cipher);
            for (int i = 0; i != -1; i = fileInputStream.read(data)) {
                cipherOutputStream.write(data, 0, i);
            }
            cipherOutputStream.close();
        }else{
            /*
             * 当输入的类型不匹配加/解密时抛出异常
             */
            throw new RuntimeException("please input type");
        }
        fileInputStream.close();
        fileOutputStream.close();
        return targetFile.getAbsolutePath();
    }
}