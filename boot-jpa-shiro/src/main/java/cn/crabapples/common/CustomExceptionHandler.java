package cn.crabapples.common;

import cn.crabapples.system.dto.ResponseDTO;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@ControllerAdvice
public class CustomExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler
    protected ResponseDTO applicationExceptionHandler(Exception e) {
        logger.warn("XHR出现异常:[{}]", e.getMessage(), e);
        if (e instanceof IncorrectCredentialsException) {
            return ResponseDTO.returnError("密码错误");
        }
        if (e instanceof AuthenticationException) {
            return ResponseDTO.returnError("用户名不存在");
        }
        if (e instanceof HttpMessageNotReadableException) {
            return ResponseDTO.returnError("参数错误");
        }
        return ResponseDTO.returnError("操作失败", e.getMessage());
    }
}
