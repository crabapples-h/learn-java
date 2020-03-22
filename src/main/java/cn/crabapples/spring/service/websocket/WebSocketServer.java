package cn.crabapples.spring.service.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/socket/{sid}")
//@ServerEndpoint(value = "/socket/{sid}", configurator = CustomSpringConfigure.class)
public class WebSocketServer {
    public ConcurrentHashMap<String, Session> webSocketMap = new ConcurrentHashMap<>(16);

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        System.err.println("当前连接的id ：" + sid);
        webSocketMap.put(sid, session);
        sendMessage(session, "连接成功");
    }

    @OnClose
    public void onClose(Session session, @PathParam("sid") String sid) {
        webSocketMap.remove(sid);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("sid") String sid) {
        sendMessage(session, "收到来自" + sid + "的信息:" + message);
        System.err.println(message);
    }

    @OnError
    public void onError(Session session, Throwable error, @PathParam("sid") String sid) {
        webSocketMap.remove(sid);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
