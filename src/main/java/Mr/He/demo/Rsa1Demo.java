package Mr.He.demo;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO RSA加密演示
 *
 * @author Mr.He
  * 2019/7/3 22:49
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public class Rsa1Demo {
    private static final Logger logger = LoggerFactory.getLogger(Rsa1Demo.class);
    private static final String CONTENT = "missyou";
    private static final String SEED = "missyou";


    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
        Map<String, Key> keyMap = createKey(SEED);
        //加密字符串
        Key publicKey = keyMap.get("publicKey");
        Key privateKey = keyMap.get("privateKey");
        String publicKeyString = keyToString(publicKey);
        String privateKeyString = keyToString(privateKey);
        logger.info("公钥：[{}]",publicKeyString);
        logger.info("私钥：[{}]",privateKeyString);
        String encodeStr = encrypt(CONTENT,publicKeyString);
        logger.info("加密后的字符串为：[{}]",encodeStr);
        String decodeStr = decrypt(encodeStr,privateKeyString);
        logger.info("还原后的字符串为：[{}]",decodeStr);
    }

    /**
     * 随机生成密钥对
     * @param seed 种子
     * @return 生成的秘钥对
     */
    public static Map<String, Key> createKey(String seed) throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom(seed.getBytes()));
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Key> keyMap = new HashMap<>(2);
        keyMap.put("publicKey",publicKey);
        keyMap.put("privateKey",privateKey);
        return keyMap;
    }
    private static <T extends Key> String keyToString(T key){
        return new String(Base64.encodeBase64(key.getEncoded()));
    }
    /**
     * RSA公钥加密
     *
     * @param content 加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    private static String encrypt(String content, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64.encodeBase64String(cipher.doFinal(content.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * RSA私钥解密
     *
     * @param content 加密字符串
     * @param privateKey 私钥
     * @return 密文
     * @throws Exception 解密过程中的异常信息
     */
    private static String decrypt(String content, String privateKey) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(content.getBytes(StandardCharsets.UTF_8));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return new String(cipher.doFinal(inputByte));
    }

}