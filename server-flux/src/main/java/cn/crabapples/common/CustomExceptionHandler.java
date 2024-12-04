package cn.crabapples.common;

import cn.crabapples.common.base.ApplicationException;
import cn.crabapples.common.base.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Component
@RestControllerAdvice
@Order(1)
public class CustomExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    protected ResponseDTO<Object> applicationExceptionHandler(Exception e) {
        logger.warn("XHR出现异常:[{}]", e.getMessage(), e);
        if (e instanceof NoHandlerFoundException) {
            return new ResponseDTO<>().returnCustom(404, "找不到指定接口", "");
        }
        if (e instanceof HttpMessageNotReadableException) {
            return new ResponseDTO<>().returnError("参数错误");
        }
        if (e instanceof ApplicationException) {
            if (401 == ((ApplicationException) e).getCode()) {
                return new ResponseDTO<>().returnAuthFail("身份认证失败");
            }
        }
        return new ResponseDTO<>().returnError("操作失败:" + e.getMessage());
    }
}
