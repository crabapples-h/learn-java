package Mr.He.spring.controller;

import Mr.He.spring.common.BaseController;
import Mr.He.spring.dto.ResponseDTO;
import Mr.He.spring.entity.User;
import Mr.He.spring.form.UserForm;
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
    public ResponseDTO addUser(@RequestBody UserForm form){
        logger.info("收到请求->添加用户:[{}]",form);
        super.validator(form, IsNotNull.class);
        User user = userService.addUser(form);
        logger.info("用户添加完成->用户信息:[{}]",user);
        return ResponseDTO.returnSuccess("添加成功",user);
    }

    @PostMapping("/editUser")
    @ApiOperation(value = "修改用户", notes = "修改用户接口")
    public ResponseDTO editUser(@RequestBody UserForm form){
        logger.info("收到请求->修改用户:[{}]",form);
        super.validator(form, Mr.He.spring.common.groups.IsNotNull.class);
        User user = userService.editUser(form);
        logger.info("用户修改完成->用户信息:[{}]",user);
        return ResponseDTO.returnSuccess("操作成功",user);
    }

}
