package cn.crabapples.system.websocket;

import cn.crabapples.common.websocket.AuthHandshakeInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO WebSocket连接端点(高级连接)
 * 第二种方式:注册WebSocketHandler
 *
 * @author Ms.He
 * 2024-11-14 23:48
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Configuration
public class WebSocketServerAdvanced implements WebSocketConfigurer {
    private final AuthHandshakeInterceptor authHandshakeInterceptor;
    public static final ConcurrentHashMap<String, WebSocketSession> WEB_SOCKET_CLIENT = new ConcurrentHashMap<>(16);

    public WebSocketServerAdvanced(AuthHandshakeInterceptor authHandshakeInterceptor) {
        this.authHandshakeInterceptor = authHandshakeInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandlerImpl(), "/ws/v2/*")
                .addInterceptors(authHandshakeInterceptor) // 添加自定义拦截器
                .setAllowedOrigins("*"); // 允许的跨域来源
    }

    @Slf4j
    public static class WebSocketHandlerImpl implements WebSocketHandler {

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            String clientId = (String) session.getAttributes().get("clientId");
            log.info("websocket连接成功,连接方式[{}],客户端ID:[{}]", "配置", clientId);
            WEB_SOCKET_CLIENT.put(clientId, session);
        }

        @Override
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
            // 处理 WebSocket 消息
            String clientId = (String) session.getAttributes().get("clientId");
            SocketMessageSender.sendMessageAdvanced(message, clientId);
//            session.sendMessage(new TextMessage("Hello, " + session.getAttributes().get("user")));
        }

        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
            System.err.println("websocket连接异常,异常信息:" + exception);
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
            log.info("websocket连接关闭,关闭方式[{}],关闭原因:[{}]", closeStatus.getCode(), closeStatus.getReason());

        }

        @Override
        public boolean supportsPartialMessages() {
            System.err.println("不支持分片消息");
            return false;
        }

//        @Override
//        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//            // 处理 WebSocket 消息
//            SocketMessageSender.sendMessage2(format, id);
//
//            session.sendMessage(new TextMessage("Hello, " + session.getAttributes().get("user")));
//        }
    }
}
