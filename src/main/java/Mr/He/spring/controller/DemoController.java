package Mr.He.spring.controller;

import Mr.He.spring.common.BaseController;
import Mr.He.spring.dto.DemoPostDTO1;
import Mr.He.spring.dto.DemoPostDTO2;
import Mr.He.spring.dto.ResponseDTO;
import Mr.He.spring.groups.IsNotNull;
import Mr.He.spring.groups.IsNull;
import Mr.He.spring.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * TODO 一个演示的controller
 * 
 * @author Mr.He
 * @date 2019/8/5 22:51
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@RestController
@RequestMapping("demo")
public class DemoController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);
    @Autowired
    DemoService demoService;

    @GetMapping("/hello")
    public String helloDemo(){
        return "hello world";
    }

    /**
     * TODO 测试从ResquestBody中获取数据并验证
     * @RequestBody 表示从请求体中获取数据
     * @Valid 表示需要验证对应属性
     *
     * 参数中传入BindingResult对象时，无论参数校验是否通过都会执行方法
     * 可通过bindingResult.hasErrors()判断参数校验是否通过
     */
    @PostMapping("/postDemo1")
    public ResponseDTO postDemo1(@Valid @RequestBody DemoPostDTO1 demoPost1DTO1, BindingResult bindingResult){
        logger.info("提交的参数:{}",demoPost1DTO1);
        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getFieldErrors().get(0).getDefaultMessage();
            logger.error("验证信息:{}", errorMessage);
            return ResponseDTO.returnError(errorMessage);
        }
        return ResponseDTO.returnSuccess("成功",demoPost1DTO1);
    }

    /**
     * TODO 测试使用不同的校验组校验参数，根据参数中的type判断使用哪个校验组
     */
    @PostMapping("/postDemo2")
    public ResponseDTO postDemo2(@RequestBody DemoPostDTO2 demoPostDTO2){
        try {
            if (demoPostDTO2.getType() == 0) {
                super.validator(demoPostDTO2, IsNull.class);
            } else {
                super.validator(demoPostDTO2, IsNotNull.class);
            }
            return ResponseDTO.returnSuccess("收到");
        }catch (Exception e){
            assert false : "" ;
            return ResponseDTO.returnError(e.getMessage());
        }
    }

}
