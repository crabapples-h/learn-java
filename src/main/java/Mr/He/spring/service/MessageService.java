package Mr.He.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

/**
 * TODO webSocketService
 *
 * @author Mr.He
 * @date 2019/8/5 22:53
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Slf4j
@Service
public class MessageService {

    @Autowired
    private WebSocketServer webSocketServer;

    public void sendMessage(String message, String sid) {
        Session session = webSocketServer.webSocketMap.get(sid);
        if (session == null) {
            return;
        }
        webSocketServer.sendMessage(session, "发往客户端的消息-->" + message);
    }
}
