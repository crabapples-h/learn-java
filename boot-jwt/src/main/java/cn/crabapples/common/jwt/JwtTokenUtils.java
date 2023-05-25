package cn.crabapples.common.jwt;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.service.SystemUserService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Base64Util;
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
@Component
public class JwtTokenUtils {

    private final JwtConfigure configure;
    private final HttpServletRequest request;
    private final SystemUserService userService;

    public JwtTokenUtils(JwtConfigure configure, HttpServletRequest request, SystemUserService userService) {
        this.configure = configure;
        this.request = request;
        this.userService = userService;
    }

    /**
     * 解析jwt
     *
     * @param jsonWebToken   token
     * @param base64Security security
     * @return 用户信息
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (ExpiredJwtException e) {
            log.warn("Token过期", e);
            throw new ApplicationException("登录状态异常", 401);
        } catch (Exception e) {
            log.warn("token解析异常", e);
            throw new ApplicationException("登录状态异常", 401);
        }
    }

    /**
     * 构建jwt
     *
     * @param userId   用户id
     * @param username 用户名
     * @return token
     */
    public String createJWT(String userId, String username) {
        try {
            // 使用HS256加密算法
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            //生成签名密钥
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(configure.getBase64Secret());
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
            //userId是重要信息，进行加密下
            String encryId = Base64Util.encode(userId);
            //添加构成JWT的参数
            JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                    // 可以将基本不重要的对象信息放到claims
                    .claim("userId", userId)
                    .setSubject(username)           // 代表这个JWT的主体，即它的所有人
                    .setIssuer(configure.getClientId())              // 代表这个JWT的签发主体；
                    .setIssuedAt(new Date())        // 是一个时间戳，代表这个JWT的签发时间；
                    .setAudience(configure.getName())          // 代表这个JWT的接收对象；
                    .signWith(signatureAlgorithm, signingKey);
            //添加Token过期时间
            int TTLMillis = configure.getExpiresSecond();
            if (TTLMillis >= 0) {
                long expMillis = nowMillis + TTLMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp)  // 是一个时间戳，代表这个JWT的过期时间；
                        .setNotBefore(now); // 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
            }
            //生成JWT
            return builder.compact();
        } catch (Exception e) {
            log.error("签名失败", e);
            throw new ApplicationException("签名失败", 401);
        }
    }

    public String login(String username, String password, SysUser sysUser) {
        if (sysUser.getPassword().equals(password)) {
            return createJWT(sysUser.getId(), username);
        }
        throw new ApplicationException("用户名或密码错误");
    }

    public SysUser getUserinfo() {
        String authHeader = request.getHeader(configure.getAuthKey());
        Claims claims = JwtTokenUtils.parseJWT(authHeader, configure.getBase64Secret());
        String userId = String.valueOf(claims.get("userId"));
        return userService.findById(userId);
    }
}
