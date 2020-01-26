package Mr.He.spring.common;

import Mr.He.spring.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * TODO 基础Controller，其他controller请继承此类
 *
 * @author Mr.He
 * @date 2019/9/21 18:28
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */

public abstract class BaseController {
    private Logger logger = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    private Validator validator;

    /**
     * 属性校验的方法
     * @param object
     */
    protected <T> void validator(Object object,Class<T> ... groups){
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object,groups);
        for (ConstraintViolation constraintViolation : constraintViolations ) {
           throw new ApplicationException(constraintViolation.getMessageTemplate());
        }
    }

    @ExceptionHandler
    protected ResponseDTO exceptionHandler(Exception e){
        logger.error("出现异常:[{}]",e.getMessage(),e);
        return ResponseDTO.returnCustom(500,e.getMessage(),null);
    }
}
