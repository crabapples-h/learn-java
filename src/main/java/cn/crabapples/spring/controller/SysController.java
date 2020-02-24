package cn.crabapples.spring.controller;

import cn.crabapples.spring.common.BaseController;
import cn.crabapples.spring.common.groups.IsLogin;
import cn.crabapples.spring.dto.ResponseDTO;
import cn.crabapples.spring.form.UserForm;
import cn.crabapples.spring.service.SysService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * TODO 系统相关接口
 *
 * @author Mr.He
 * 2020/1/28 23:17
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */

@Controller
@Api("系统管理")
public class SysController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private SysService sysService;

    public SysController(SysService sysService) {
        this.sysService = sysService;
    }

    @GetMapping("/")
    public String index(){
        logger.info("收到请求->进入主页");
        return "index";
    }

    @PostMapping("/login")
    @ResponseBody
    @ApiOperation(value = "用户登陆", notes = "用户登陆接口")
    public ResponseDTO login(@RequestBody UserForm form){
        logger.info("收到请求->用户登陆:[{}]",form);
        super.validator(form, IsLogin.class);
        String token = sysService.login(form);
        logger.info("登录成功->用户信息:[{}]",token);
        return ResponseDTO.returnSuccess("操作成功",token);
    }


}
