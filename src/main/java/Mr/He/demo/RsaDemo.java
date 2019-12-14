package Mr.He.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加密演示
 * @author crabapples.cn@gmail.com
 * @date 2019/7/3 23:22
 */
public class RsaDemo {
    private static final Logger logger = LoggerFactory.getLogger(RsaDemo.class);
    private static final String CONTENT = "missyou";
    private static final String SEED = "missyou";

    public static void main(String[] args) throws Exception {
        Map<String, Key> keyMap = createKey(SEED);
        String publicKeyString = keyToString(keyMap.get("publicKay"));
        String privateKeyString = keyToString(keyMap.get("privateKey"));
        logger.info("公钥：[{}]",publicKeyString);
        logger.info("私钥：[{}]",privateKeyString);
        String encodeStr = encode(keyMap.get("publicKay"),CONTENT);
        logger.info("加密后的字符串为：[{}]",encodeStr);
        String decodeStr = decode(keyMap.get("privateKey"),encodeStr);
        logger.info("还原后的字符串为：[{}]",decodeStr);
    }

    /**
     * 执行解密操作
     * @param key 私钥
     * @param encodeStr 需要解密的字符串
     * @return 解密之后的字符串
     * @throws Exception
     */
    private static String decode(Key key,String encodeStr) throws Exception {
        /**
         * 使用字符串作为密钥时需要转回key
         * String keyString = new String(Base64.getEncoder().encode(key.getEncoded()));
         * byte[] keyByte = Base64.getDecoder().decode(keyString);
         * RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(keyByte));
         *
         */
        byte[] strByte = Base64.getDecoder().decode(encodeStr);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] decodeByte = cipher.doFinal(strByte);
        return new String(decodeByte);
    }

    /**
     * 执行加密操作
     * @param key 公钥
     * @return 加密后的字符串
     * @throws Exception
     */
    private static String encode(Key key,String content) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] encodeByte = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        byte[] base64Byte = Base64.getEncoder().encode(encodeByte);
        return new String(base64Byte);
    }

    /**
     * 根据种子生成随机秘钥
     * @param seed 种子
     * @return 生成的秘钥
     */
    private static Map<String, Key> createKey(String seed) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(512,new SecureRandom(seed.getBytes()));
        KeyPair keyPair = keyPairGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        Map<String, Key> key = new HashMap<>(2);
        key.put("publicKay",publicKey);
        key.put("privateKey",privateKey);
        return key;
    }

    /**
     * 将key转换为字符串
     * @param key key
     * @param <T> key类型
     * @return 转换后的字符串
     */
    private static <T extends Key> String keyToString(T key){
         byte[] keyByte = key.getEncoded();
         return new String (Base64.getEncoder().encode(keyByte));
    }

}
