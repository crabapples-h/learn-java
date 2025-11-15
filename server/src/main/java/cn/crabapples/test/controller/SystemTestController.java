package cn.crabapples.test.controller;

import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.annotation.JwtIgnore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;

/**
 * TODO 测试接口
 *
 * @author Mr.He
 * 2022/5/6 20:44:34
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@Controller
//@Api("测试接口")
@Slf4j
//@RequestMapping("/api/test")
@RequestMapping("/releases")
public class SystemTestController extends BaseController {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public SystemTestController(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 获取网络请求信息
     */
    @JwtIgnore
    @RequestMapping(value = "/4.5.3")
//    @ApiOperation(value = "信息", notes = "获取网络请求信息")
    public void info() throws IOException {
        log.info("收到请求->获取网络请求信息");
        String uri = request.getRequestURI();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String contextPath = request.getContextPath();
        String contentType = request.getContentType();
        String remoteAddr = request.getRemoteAddr();
        int remotePort = request.getRemotePort();
        String remoteHost = request.getRemoteHost();
        Enumeration<String> headerNames = request.getHeaderNames();
        for (String header = headerNames.nextElement(); headerNames.hasMoreElements(); header = headerNames.nextElement()) {
            String value = request.getHeader(header);
            log.info("请求头信息:[header:{}][value:{}]", header, value);
        }
        log.info("请求信息:\n[uri:{}]\n[url:{}]\n[method:{}]\n[contextPath:{}]\n[contentType:{}]\n" +
                        "[remoteAddr:{}]\n[remotePort:{}]\n[remoteHost:{}]",
                uri, url, method, contextPath, contentType, remoteAddr, remotePort, remoteHost);
        ServletOutputStream outputStream = response.getOutputStream();
        String fileName = "opencv-4.5.3-vc14_vc15.exe";
        FileInputStream inputStream = new FileInputStream("C:\\Users\\mshe\\Downloads\\"+fileName);
        response.setHeader("content-disposition","attachment;fileName="+fileName);
        byte[] data = new byte[1024];
        for (int i = 0; i != -1; i = inputStream.read(data)) {
            outputStream.write(data,0,i);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
}
