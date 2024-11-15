package cn.crabapples.common.jwt;

import cn.crabapples.common.base.ApplicationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Date;


/**
 * TODO jwt工具(用于解析和生成token)
 *
 * @author Mr.He
 * 9/5/20 2:52 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Slf4j
@Getter
@Setter
@Component
@PropertySource(value = {"classpath:application-custom.properties"})
@ConfigurationProperties(prefix = "crabapples.jwt")
public class JwtTokenUtils {
    private String authKey; // 授权信息在header中的key
    private String base64Secret; // 密钥
    private String issuer; // 签发主体
    private String audience; // 接收主体
    private long expiresSecond; // token过期时间(秒)

    private final HttpServletRequest request;

    public JwtTokenUtils(HttpServletRequest request) {
        this.request = request;
    }

    public String getUserId() {
        String token = request.getHeader(authKey);
        return getUserId(token);
    }

    public String getUserId(String token) {
        return valid(token).getSubject();
    }

    public String getUserName() {
        String token = request.getHeader(authKey);
        return getUserName(token);
    }

    public String getUserName(String token) {
        return valid(token).getClaim("username").asString();
    }

    private static KeyPair createKey(String seed) throws NoSuchAlgorithmException {
//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec("seed".getBytes());
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
//        PublicKey publicKey = keyFactory.generatePublic(keySpec);

//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(seed.getBytes());
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        Provider provider = keyFactory.getProvider();

        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048, new SecureRandom(seed.getBytes()));
        return keyPairGen.generateKeyPair();
    }

    public DecodedJWT valid(String token) {
        try {
            // 使用RSA时会无法正常验证token，暂时没找到解决方案
//            RSAPublicKey publicKey = (RSAPublicKey) createKey("seed").getPublic();
//            RSAPrivateKey privateKey = (RSAPrivateKey) createKey("seed").getPrivate();
//            JWT.require(Algorithm.RSA256(publicKey, privateKey)).build().verify(token);
            return JWT.require(Algorithm.HMAC256(base64Secret)).build().verify(token);
        } catch (Exception e) {
            log.warn("Token验证失败", e);
            throw new ApplicationException("token验证失败");
        }
    }

    public String createToken(String subject, String username) {
        try {
            long nowMillis = System.currentTimeMillis();
            Date now = new Date();
            Date exp = new Date(nowMillis + expiresSecond * 1000);
            return JWT.create()
                    .withHeader(Collections.singletonMap("type", "LOGIN"))
                    .withClaim("username", username)
                    .withSubject(subject) // JWT的所有人
                    .withIssuer(issuer)   // JWT的签发主体
                    .withIssuedAt(now) //JWT的签发时间
                    .withExpiresAt(exp) // JWT的过期时间
                    .withNotBefore(now) // JWT生效的开始时间
                    .withAudience(audience)// 这个JWT的接收对象
                    .sign(Algorithm.HMAC256(base64Secret));
        } catch (UnsupportedEncodingException e) {
            throw new ApplicationException("token生成失败");
        }
    }
}
