package cn.crabapples.system.websocket;

import cn.crabapples.common.websocket.EndPointConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO WebSocket连接端点(简单连接)
 * 第一种方式：使用@ServerEndpoint注解定义的WebSocket服务端点
 *
 * @author Ms.He
 * 2024-11-14 21:10
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Slf4j
@Component
@ServerEndpoint(value = "/ws/v1/{id}", configurator = EndPointConfigure.class)
public class WebSocketServerSimple {

    public static final ConcurrentHashMap<String, Session> WEB_SOCKET_CLIENT = new ConcurrentHashMap<>(16);

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) throws IOException {
        WEB_SOCKET_CLIENT.put(id, session);
//        String userId = jwtTokenUtils.getUserId(token);
//        String username = jwtTokenUtils.getUserName(token);
//        attributes.put("clientId", clientId);
//        attributes.put("userId", userId);
//        attributes.put("username", username);
        log.info("WebSocket连接成功,客户端ID:[{}],当前连接数量:[{}]", id, WEB_SOCKET_CLIENT.size());
        SocketMessageSender.sendMessageSimple("连接成功", id);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("id") String id) {
        log.info("客户端[{}]连接断开", id);
        WEB_SOCKET_CLIENT.remove(id);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(Session session, String message, @PathParam("id") String id) throws IOException {
        String format = MessageFormat.format("收到来自客户端[{0}]消息:[{1}]", id, message);
        log.info(format);
        SocketMessageSender.sendMessageSimple(format, id);

    }

    @OnError
    public void onError(Session session, Throwable error, @PathParam("id") String id) {
        error.printStackTrace();
        log.info("客户端[{}]连接错误:[{}]", id, error.getMessage());
        WEB_SOCKET_CLIENT.remove(id);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
