package demo.security;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 谷歌令牌验证演示
 *
 * @author Mr.He
 * 12/14/19 7:36 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class GoogleAuthenticatorDemo {
    private static final Logger logger = LoggerFactory.getLogger(GoogleAuthenticatorDemo.class);
    private static final String FORMAT = "http://www.google.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s@%s?secret=%s";

    public static void main(String[] args) {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        String key = googleAuthenticator.createCredentials().getKey();
        logger.info("秘钥:[{}]",key);
        String codeUrl = getQRBarcodeURL("hequan","crabapples.cn",key);
        logger.info("二维码地址:[{}]",codeUrl);
        boolean status = googleAuthenticator.authorize(key, 123456);
        logger.info("验证结果:[{}]",status);

    }

    public static String getQRBarcodeURL(String user, String host, String secret) {
        return String.format(FORMAT, user, host, secret);
    }
}



