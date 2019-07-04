package Mr.He.spring.controller;

import Mr.He.spring.entity.WebsocketMessage;
import Mr.He.spring.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/websocket/")
public class WebSocketController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("index")
    public String index() {
        System.err.println(666);
        return "SocketDemo";
    }

    @ResponseBody
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public void sengMeaasge(@RequestBody WebsocketMessage message) {
        messageService.publishApp(message.getMessage(), message.getSid());
    }
}
