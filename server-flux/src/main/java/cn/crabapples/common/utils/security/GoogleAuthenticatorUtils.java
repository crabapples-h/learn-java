package cn.crabapples.common.utils.security;

import cn.crabapples.common.utils.QRCodeUtils;
import lombok.Data;
import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * TODO google身份验证器
 *
 * @author Mr.He
 * 12/14/19 7:12 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */

public class GoogleAuthenticatorUtils {


    private static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";
    // 生成的key长度( Generate secret key length)
    private static final int KEY_LENGTH = 10;
    // 最多可偏移的时间，默认3，最大值17
    private static int windowSize = 3;

    public static void main(String[] args) {
        AuthData auth = createNewAuth("admin");
        System.out.println(auth);
        boolean status = checkCode(auth.secret, 123321, System.currentTimeMillis());
        System.err.println(status);
    }

    public static AuthData createNewAuth(String user) {
        try {
            String secretKey = generateSecretKey();
            String qrBarcode = getQRBarcode(user, secretKey);
            String url = QRCodeUtils.createOnlineQrcode(qrBarcode);
            AuthData authData = new AuthData();
            authData.secret = secretKey;
            authData.content = qrBarcode;
            authData.user = user;
            authData.url = url;
            return authData;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("令牌生成失败");
        }
    }

    /**
     * 设置窗口大小1~17之间
     *
     * @param time 允许的30秒次数 time*30
     */
    public void setWindowSize(int time) {
        if (time >= 1 && time <= 17)
            windowSize = time;
    }

    /**
     * 生成随机密钥。必须由服务器保存并与用户帐户相关联，以验证Google身份验证器显示的代码。
     * 用户必须在其设备上注册此密钥
     *
     * @return secret key
     */
    public static String generateSecretKey() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance(RANDOM_NUMBER_ALGORITHM);
        byte[] buffer = secureRandom.generateSeed(KEY_LENGTH);
        Base32 codec = new Base32();
        byte[] bEncodedKey = codec.encode(buffer);
        return new String(bEncodedKey);
    }

    /**
     * 生成一个google身份验证器，识别的字符串，只需要把该方法返回值生成二维码扫描就可以了
     *
     * @param user   账号
     * @param secret 密钥
     * @return
     */
    public static String getQRBarcode(String user, String secret) {
        String format = "otpauth://totp/%s?secret=%s";
        return String.format(format, user, secret);
    }


    /**
     * 验证code是否合法
     *
     * @param secret 用户的secret.
     * @param code   用户设备上显示的代码
     * @param timeMs 时间（毫秒） (System.currentTimeMillis() for example)
     * @return 是否合法
     */
    public static boolean checkCode(String secret, long code, long timeMs) {
        try {
            Base32 codec = new Base32();
            byte[] decodedKey = codec.decode(secret);
            // 将unix毫秒时间转换为30秒的“窗口”，这是根据TOTP规范进行的（有关详细信息，请参阅RFC）
            long time = (timeMs / 1000L) / 30L;
            // 窗口用于检查最近生成的代码,可以使用此值来调整验证的窗口范围
            for (int i = -windowSize; i <= windowSize; ++i) {
                long hash = verifyCode(decodedKey, time + i);
                if (hash == code) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 生成验证码
     *
     * @param key   密钥
     * @param value 时间窗口
     * @return 验证码
     */
    private static int verifyCode(byte[] key, long value) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] data = new byte[8];
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);
        int offset = hash[20 - 1] & 0xF;
        // 我们使用long是因为Java没有unsigned int
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            // 处理有符号的字节,只保留第一个字节。
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;
        return (int) truncatedHash;
    }

    @Data
    public static class AuthData {
        private String secret;
        private String user;
        private String content;
        private String url;
    }
}
