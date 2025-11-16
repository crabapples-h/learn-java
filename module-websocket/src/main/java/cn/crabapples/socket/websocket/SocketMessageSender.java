package cn.crabapples.socket.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO WebSocket消息发送服务
 *
 * @author Mr.He
 * 2019/8/5 22:53
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Slf4j
public class SocketMessageSender {
    /**
     * 简单连接 v1版
     * 向所有客户端发送消息
     *
     * @param message 消息
     * @throws IOException IO异常
     */
    public static void sendMessageAllSimple(String message) throws IOException {
        ConcurrentHashMap.KeySetView<String, Session> keys = cn.crabapples.socket.websocket.WebSocketServerSimple.WEB_SOCKET_CLIENT.keySet();
        for (String key : keys) {
            Session session = WebSocketServerSimple.WEB_SOCKET_CLIENT.get(key);
            session.getBasicRemote().sendText(message);
        }
    }

    /**
     * 简单连接 v1版
     * 向指定客户端发送消息
     *
     * @param message 消息
     * @param id      客户端ID
     * @throws IOException IO异常
     */
    public static void sendMessageSimple(String message, String id) throws IOException {
        Session session = cn.crabapples.socket.websocket.WebSocketServerSimple.WEB_SOCKET_CLIENT.get(id);
        if (session == null) {
            return;
        }
        session.getBasicRemote().sendText(message);
    }

    /**
     * 高级连接 v2版
     * 向所有客户端发送消息
     *
     * @param message 消息
     * @throws IOException IO异常
     */
    public static void sendMessageAllAdvanced(WebSocketMessage<?> message) throws IOException {
        ConcurrentHashMap.KeySetView<String, WebSocketSession> keys = WebSocketServerAdvanced.WEB_SOCKET_CLIENT.keySet();
        for (String key : keys) {
            WebSocketSession session = cn.crabapples.socket.websocket.WebSocketServerAdvanced.WEB_SOCKET_CLIENT.get(key);
            session.sendMessage(message);
        }
    }

    /**
     * 高级连接 v2版
     * 向指定客户端发送消息
     *
     * @param message 消息
     * @param id      客户端ID
     * @throws IOException IO异常
     */
    public static void sendMessageAdvanced(WebSocketMessage<?> message, String id) throws IOException {
        WebSocketSession session = cn.crabapples.socket.websocket.WebSocketServerAdvanced.WEB_SOCKET_CLIENT.get(id);
        if (session == null) {
            return;
        }
        session.sendMessage(message);
    }
}
