package cn.crabapples.socket.websocket;

import cn.crabapples.common.websocket.AuthHandshakeInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.MessageFormat;
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
        registry.addHandler(new WebSocketHandlerImpl(), "/websocket/v2/*")
                .addInterceptors(authHandshakeInterceptor) // 添加自定义拦截器
                .setAllowedOrigins("*"); // 允许的跨域来源
    }

    @Slf4j
    public static class WebSocketHandlerImpl implements WebSocketHandler {

        @Override
        public void afterConnectionEstablished(WebSocketSession session) {
            String clientId = (String) session.getAttributes().get("clientId");
            log.info("websocket连接成功,连接方式[{}],客户端ID:[{}]", "配置", clientId);
            WEB_SOCKET_CLIENT.put(clientId, session);
        }

        /**
         * 处理 WebSocket 消息
         * <p>
         * 1.文本数据类型消息 TextMessage 普通文本类消息
         * TextMessage message = new TextMessage("Hello world");
         * <p>
         * 2.二进制数据类型消息 BinaryMessage 二进制数据类消息,可通过一定方式解析为各种数据
         * BinaryMessage message = new BinaryMessage(ByteBuffer.wrap("Hello world".getBytes()))
         * <p> 实际测试时后两种消息客户端无法收到,后续研究
         * 3.心跳消息 PingMessage 用于检测长连接是否正常.可以携带二进制数据也可以不携带数据
         * PingMessage message = new PingMessage(ByteBuffer.wrap("Hello ping".getBytes()));
         * <p>
         * 4.心跳响应消息 PongMessage 用于响应心跳消息,当收到ping消息时,对方应回应pong消息.可以携带二进制数据也可以不携带数据
         * PongMessage message = new PongMessage(ByteBuffer.wrap("Hello ping".getBytes()));
         *
         * @param session 长连接session
         * @param message 客户端发送的消息
         */
        @Override
        public void handleMessage(WebSocketSession session, @NotNull WebSocketMessage<?> message) throws IOException {
            // 处理 WebSocket 消息
            String clientId = (String) session.getAttributes().get("clientId");
            String format = MessageFormat.format("收到来自客户端[{0}]消息:[{1}]", clientId, message.getPayload());
            log.info(format);
            TextMessage textMessage = new TextMessage(format);
            SocketMessageSender.sendMessageAdvanced(textMessage, clientId);

//            session.sendMessage(new TextMessage("Hello, " + session.getAttributes().get("user")));
        }

        @Override
        public void handleTransportError(@NotNull WebSocketSession session, @NotNull Throwable exception) throws Exception {
            System.err.println("websocket连接异常,异常信息:" + exception);
        }

        @Override
        public void afterConnectionClosed(@NotNull WebSocketSession session, CloseStatus closeStatus) throws Exception {
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
