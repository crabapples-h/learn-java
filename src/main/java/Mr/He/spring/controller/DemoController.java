package Mr.He.spring.controller;

import Mr.He.spring.dto.UserDTO;
import Mr.He.spring.entity.DemoUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * TODO 一个演示的controller
 * 
 * @author Mr.He
 * @date 2019/8/5 22:51
 * e-mail wishforyou.xia@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@RestController
@RequestMapping("demo")
public class DemoController {
    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);
    @GetMapping("/hello")
    public String helloDemo(){
        return "hello world";
    }
    @PostMapping("/postUser")
    public UserDTO postDemo(@RequestBody @Valid UserDTO userDto){
        logger.info("提交的参数:{}",userDto);
        return userDto;
    }
}
