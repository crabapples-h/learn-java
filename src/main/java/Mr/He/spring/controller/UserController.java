package Mr.He.spring.controller;

import Mr.He.spring.common.BaseController;
import Mr.He.spring.dto.ResponseDTO;
import Mr.He.spring.entity.User;
import Mr.He.spring.groups.IsNotNull;
import Mr.He.spring.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * TODO 用户管理接口类
 *
 * @author Mr.He
 * 2020/1/27 2:09
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Controller
@ResponseBody
@RequestMapping(value = "/api/user")
@Api("用户管理")
public class UserController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    @ApiOperation(value = "添加用户", notes = "添加用户接口")
    public ResponseDTO addUser(@RequestBody User user){
        logger.info("收到请求->添加用户:[{}]",user);
        super.validator(user, IsNotNull.class);
        User newUser = userService.addUser(user);
        logger.info("用户添加完成->用户信息:[{}]",newUser);
        return ResponseDTO.returnSuccess("添加成功",newUser);
    }

}
