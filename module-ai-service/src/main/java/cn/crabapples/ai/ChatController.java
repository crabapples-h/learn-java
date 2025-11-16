package cn.crabapples.ai;

//import org.springframework.ai.chat.client.ChatClient;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class ChatController {
    private final OpenAiChatModel chatModel;
//    private final ChatClient chatClient;


//    // 修改构造函数，注入 ChatClient.Builder
    public ChatController(
//            ChatClient.Builder chatClientBuilder,
            OpenAiChatModel chatModel) {
//        this.chatClient = chatClientBuilder.build();
        this.chatModel = chatModel;
    }


    @GetMapping("/ai/generate")
    public Map<String, String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
//        String call = this.chatClient.call(message);
//        System.err.println(call);
//        return Collections.singletonMap("generation", call);
        return null;
    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
//        return this.chatModel.stream(prompt);
        return null;

    }
}
