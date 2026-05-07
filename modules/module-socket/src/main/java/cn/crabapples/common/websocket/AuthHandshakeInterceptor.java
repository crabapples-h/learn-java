package cn.crabapples.common.websocket;

import cn.crabapples.common.base.ApplicationException;
import cn.crabapples.common.jwt.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * TODO 使用配置方式的拦截器
 *
 * @author Mr.He
 * 2024-11-14 23:51
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Slf4j
@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {
    private static final String URL_PREFIX = "/api/stream/websocket/v2/";

    private final JwtTokenUtils jwtTokenUtils;

    public AuthHandshakeInterceptor(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        HttpHeaders headers = request.getHeaders();
        URI uri = request.getURI();
        String path = uri.getPath();
        String clientId = path.replaceFirst("/websocket/", "").replace(URL_PREFIX,"");
        List<String> tokenList = headers.get("sec-websocket-protocol");
        headers.remove("sec-websocket-protocol");
        if (Objects.nonNull(tokenList) && !tokenList.isEmpty()) {
            String token = tokenList.get(0);
            // 检查 Token 是否存在，并进行验证
            if (null != token) {
                // Token 验证通过，可以将用户信息添加到 WebSocket session attributes 中
                try {
                    jwtTokenUtils.valid(token);
                    String userId = jwtTokenUtils.getUserId(token);
                    String username = jwtTokenUtils.getUserName(token);
                    attributes.put("clientId", clientId);
                    attributes.put("userId", userId);
                    attributes.put("username", username);
                } catch (ApplicationException e) {
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return false;
                }
                response.getHeaders().put("sec-websocket-protocol", Collections.singletonList(token));
                // 允许握手
                return true;
            } else {
                // Token 验证失败，拒绝握手
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return false;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler
            wsHandler, Exception exception) {
        // 握手完成后的处理逻辑（可以留空）
    }

}
