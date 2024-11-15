package cn.crabapples.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * TODO
 *
 * @author Mr.He
 * 2024-11-14 10:54
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
//@Component
//@ControllerAdvice
public class ResponseBodyHandler implements ResponseBodyAdvice {
    @Value("${crypt:false}")
    private boolean crypt;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType, Class selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        System.err.println("body:" + body);
        return body;
    }
}
