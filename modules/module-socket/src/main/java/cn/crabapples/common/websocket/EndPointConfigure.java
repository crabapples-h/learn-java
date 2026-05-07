package cn.crabapples.common.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * TODO 使用注解方式的拦截器
 *
 * @author Mr.He
 * 2024-11-14 23:50
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Configuration
public class EndPointConfigure extends ServerEndpointConfig.Configurator {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        /*
         * ServerEndpointConfig 继承至EndpointConfig
         * HandshakeRequest 接口代表一个请求升级到WebSocket的HTTP请求
         * HandshakeResponse 接口代表一个响应升级到WebSocket的HTTP响应
         */
        //获取请求连接中携带的头和参数；
        Map<String, List<String>> headers = request.getHeaders();
        List<String> tokenList = headers.get("sec-websocket-protocol");
        if (null != tokenList && !tokenList.isEmpty()) {
            String token = tokenList.get(0);
            response.getHeaders().put("Sec-WebSocket-Protocol", Collections.singletonList(token));
        }
        //...可以验证码头信息的合法性或者直接存储到用户配置里面
//        sec.getUserProperties().put("token", "6666666");
        super.modifyHandshake(sec, request, response);
    }
}
