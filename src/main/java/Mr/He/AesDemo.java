package Mr.He;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES加密演示
 * @author wishforyou.xia@gmail.com
 * @date 2019/7/3 23:24
 */
public class AesDemo {
    private static final String STRING = "missyou";

    public static void main(String[] args) throws Exception {
        Key key = createKey();
        String keyString = new String(Base64.getEncoder().encode(key.getEncoded()));
        System.out.println("密钥：" + keyString);

        String encodeString = encode(key);
        System.err.println("加密后的字符串为:" + encodeString);

        String decodeString = decode(key,encodeString);
        System.out.println("还原后的字符串为:" + decodeString);
    }

    private static Key createKey() throws Exception{
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128,new SecureRandom("missyou".getBytes()));
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey;
    }

    private static String encode(Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] dataByte = cipher.doFinal(STRING.getBytes());
        byte[] encodeByte = Base64.getEncoder().encode(dataByte);
        String data = new String(encodeByte);
        return data;
    }

    private static String decode(Key key, String string) throws Exception {
        byte[] decodeByte = Base64.getDecoder().decode(string.getBytes());
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] dataByte = cipher.doFinal(decodeByte);
        String data = new String(dataByte);
        return data;
    }
}