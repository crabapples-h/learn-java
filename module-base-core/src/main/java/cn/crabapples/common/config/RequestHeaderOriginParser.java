package cn.crabapples.common.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取请求头中的origin属性,供sentinel进行限流使用
 * origin在网关过滤器中已经设置为api-gateway
 * 如果是网关请求，则origin为api-gateway,否则origin为空
 *
 * @author Ms.He
 * 2025-11-20 02:04
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Component
public class RequestHeaderOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        String origin = httpServletRequest.getHeader("origin");
        if (!StringUtils.hasLength(origin)) {
            return origin;
        }
        return "black";
    }
}
