package cn.crabapples.system.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * TODO SSE接口(这个接口在node环境下会有问题，暂时只能在纯浏览器环境打开)
 *
 * @author Mr.He
 * 2021/6/16 9:14
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name admin
 */
@Controller
@RequestMapping("/sse/")
@Slf4j
public class ServerSentEventController {

    //    @ResponseBody
    @RequestMapping(value = "/demo")
    public SseEmitter demo(HttpServletRequest request) throws IOException, InterruptedException {
        SseEmitter sseEmitter = new SseEmitter(1000L * 60 * 60);
        new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    String message = "hello" + i;
                    sseEmitter.send(message);
                    log.info(message);
                    TimeUnit.SECONDS.sleep(1);
                }
                sseEmitter.complete();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        return sseEmitter;
    }

}
