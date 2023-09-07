package cn.crabapples.system.controller;

import cn.crabapples.common.Groups;
import cn.crabapples.common.ResponseDTO;
import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.jwt.JwtIgnore;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.SystemService;
import cn.crabapples.system.service.SystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO 系统相关接口
 *
 * @author Mr.He
 * 2020/1/28 23:17
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */

@RestController
@RequestMapping("/api/system/")
@Slf4j
@Api("系统功能")
public class SystemController extends BaseController {

    private final SystemUserService userService;
    private final SystemService sysService;

    public SystemController(SystemUserService userService, SystemService sysService) {
        this.userService = userService;
        this.sysService = sysService;
    }

    /**
     * 发起登录请求
     *
     * @param form 用户名和密码
     * @return 登录成功返回token
     */
    @JwtIgnore
    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登陆", notes = "用户登陆接口")
    public ResponseDTO login(@RequestBody UserForm form) {
        log.info("收到请求->用户登陆验证:[{}]", form);
        super.validator(form, Groups.IsLogin.class);
        String token = sysService.login(form);
        log.info("返回结果->登录成功->token:[{}]", token);
        return ResponseDTO.returnSuccess("登录成功", token);
    }

    /**
     * 注销登录
     */
    @JwtIgnore
    @PostMapping("/logout")
    @ApiOperation(value = "注销登录", notes = "注销登录接口")
    public ResponseDTO logout() {
        return ResponseDTO.returnSuccess("注销成功");
    }

    @GetMapping("/permissions")
    public ResponseDTO getUserPermissions() {
        log.info("收到请求->获取用户权限列表");
        List<String> list = sysService.getUserPermissions();
        log.info("返回结果->获取用户权限列表成功:[{}]", list);
        return ResponseDTO.returnSuccess(list);
    }

    @GetMapping("/userInfo")
    @ApiOperation(value = "获取当前用户信息", notes = "获取当前用户信息接口")
    public ResponseDTO getUserInfo() {
        log.info("收到请求->获取当前用户信息");
        SysUser entity = userService.getUserInfo();
        log.info("返回结果->获取当前用户信息结束:[{}]", entity);
        return ResponseDTO.returnSuccess(entity);
    }

    @GetMapping("/checkUsername/{username}")
    @ApiOperation(value = "检测用户名是否被使用", notes = "检测用户名是否被使用接口")
    public ResponseDTO checkUsername(@PathVariable String username) {
        log.info("收到请求->检测用户名是否被使用:[{}]", username);
        boolean exist = sysService.checkUsername(username);
        log.info("返回结果->检测用户名是否被使用:[{}]", exist);
        return ResponseDTO.returnSuccess("用户名未被使用", exist);
    }
}
