package cn.crabapples.common.jwt;

import cn.crabapples.common.ApplicationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
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
    private String clientId;
    private String base64Secret;
    private String name;
    private int expiresSecond;

    private final HttpServletRequest request;

    public JwtTokenUtils(HttpServletRequest request) {
        this.request = request;
    }

//    /**
//     * 解析jwt
//     *
//     * @param jsonWebToken   token
//     * @param base64Security
//     * @return
//     */
//    public static Claims parseJWT(String jsonWebToken, String base64Security) {
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
//                    .parseClaimsJws(jsonWebToken).getBody();
//            return claims;
//        } catch (ExpiredJwtException e) {
//            log.warn("Token过期", e);
//            throw new ApplicationException("登录状态异常", 401);
//        } catch (Exception e) {
//            log.warn("token解析异常", e);
//            throw new ApplicationException("登录状态异常", 401);
//        }
//    }

    public String getUserId() {
        String token = request.getHeader(authKey);
        return parseToken(token);
    }

    public String parseToken(String token) {
        Claims body;
        try {
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Secret);
            // 使用HS256加密算法
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
            body = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.warn("Token过期", e);
            throw new ApplicationException("登录状态异常", 401);
        } catch (Exception e) {
            log.warn("token解析异常", e);
            throw new ApplicationException("登录状态异常", 401);
        }
        return body.getSubject();
    }
//
//    /**
//     * 构建jwt
//     *
//     * @param userId
//     * @param username
//     * @param jwtConfigure
//     * @return
//     */
//    public static String createJWT(String userId, String username, JwtConfigure jwtConfigure) {
//        try {
//            // 使用HS256加密算法
//            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//            long nowMillis = System.currentTimeMillis();
//            Date now = new Date(nowMillis);
//            //生成签名密钥
//            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtConfigure.getBase64Secret());
//            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
//            //userId是重要信息，进行加密下
//            String encryId = Base64Util.encode(userId);
//            //添加构成JWT的参数
//            JwtBuilder builder = Jwts.builder()
//                    .setHeaderParam("type", "JWT")
//                    // 可以将基本不重要的对象信息放到claims
//                    .claim("userId", userId)
//                    .setSubject(username)           // 代表这个JWT的主体，即它的所有人
//                    .setIssuer(jwtConfigure.getClientId())              // 代表这个JWT的签发主体；
//                    .setIssuedAt(new Date())        // 是一个时间戳，代表这个JWT的签发时间；
//                    .setAudience(jwtConfigure.getName())          // 代表这个JWT的接收对象；
//                    .signWith(signatureAlgorithm, signingKey);
//            //添加Token过期时间
//            int TTLMillis = jwtConfigure.getExpiresSecond();
//            if (TTLMillis >= 0) {
//                long expMillis = nowMillis + TTLMillis;
//                Date exp = new Date(expMillis);
//                builder.setExpiration(exp)  // 是一个时间戳，代表这个JWT的过期时间；
//                        .setNotBefore(now); // 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
//            }
//
//            //生成JWT
//            return builder.compact();
//        } catch (Exception e) {
//            log.error("签名失败", e);
//            throw new ApplicationException("签名失败", 401);
//        }
//    }

    public String createToken(String subject, String username) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + expiresSecond);
        // 使用HS256加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .claim("username", username)
                // 这个JWT的主体，即它的所有人
                .setSubject(subject)
                // 这个JWT的签发主体
                .setIssuer("jwtConfigure.getClientId()")
                // 这个JWT的接收对象
                .setAudience("jwtConfigure.getName()")
                .signWith(signatureAlgorithm, signingKey)
                // 是一个时间戳，这个JWT的签发时间；
                .setIssuedAt(new Date())
                // 是一个时间戳，这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
                .setNotBefore(now)
                // 是一个时间戳，这个JWT的过期时间；
                .setExpiration(exp)
                .compact();
    }

  public String createToken(String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + expiresSecond);
          // 使用HS256加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
//                .claim("username", username)
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .setIssuedAt(new Date())
                .setNotBefore(now)
                .setExpiration(exp)
                .compact();
    }

}
