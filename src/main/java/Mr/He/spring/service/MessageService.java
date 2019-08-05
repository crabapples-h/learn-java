package Mr.He.spring.service;

        import lombok.extern.slf4j.Slf4j;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import javax.websocket.Session;

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
