package cn.crabapples.system.controller;

import cn.crabapples.common.BaseController;
import cn.crabapples.common.groups.IsAdd;
import cn.crabapples.common.groups.IsEdit;
import cn.crabapples.common.groups.IsStatusChange;
import cn.crabapples.system.dto.ResponseDTO;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RestController
@RequestMapping(value = "/api/user")
@Api("用户管理")
public class UserController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    @ApiOperation(value = "添加用户", notes = "添加用户接口")
    public ResponseDTO addUser(@RequestBody cn.crabapples.system.form.UserForm form) {
        logger.info("收到请求->添加用户:[{}]", form);
        super.validator(form, IsAdd.class);
        SysUser user = userService.addUser(form);
        logger.info("返回结果->用户添加完成:[{}]", user);
        return ResponseDTO.returnSuccess("操作成功", user);
    }

    @PostMapping("/editUser")
    @ApiOperation(value = "修改用户", notes = "修改用户接口")
    public ResponseDTO editUser(@RequestBody cn.crabapples.system.form.UserForm form) {
        logger.info("收到请求->修改用户:[{}]", form);
        super.validator(form, IsEdit.class);
        SysUser user = userService.editUser(form);
        logger.info("返回结果->用户修改完成:[{}]", user);
        return ResponseDTO.returnSuccess("操作成功", user);
    }

    @PostMapping("/delUser")
    @ApiOperation(value = "删除用户", notes = "删除用户接口")
    public ResponseDTO delUser(@RequestBody cn.crabapples.system.form.UserForm form) {
        super.validator(form, cn.crabapples.common.groups.IsStatusChange.class);
        logger.info("收到请求->删除用户:[{}]", form.getId());
        userService.delUser(form.getId());
        logger.info("返回结果->用户删除完成");
        return ResponseDTO.returnSuccess("操作成功");
    }

    @PostMapping("/changeStatus")
    @ApiOperation(value = "修改用户状态", notes = "修改用户状态接口")
    public ResponseDTO changeStatus(@RequestBody UserForm form) {
        super.validator(form, IsStatusChange.class);
        logger.info("收到请求->修改用户状态,id:[{}]", form.getId());
        userService.changeStatus(form.getId());
        logger.info("返回结果->修改用户状态完成");
        return ResponseDTO.returnSuccess("操作成功");
    }

    @GetMapping("/getUserInfo")
    @ApiOperation(value = "获取当前用户信息", notes = "获取当前用户信息接口")
    public ResponseDTO getUserInfo() {
        logger.info("收到请求->获取当前用户信息");
        SysUser sysUser = userService.getUserInfo();
        logger.info("返回结果->获取当前用户信息结束:[{}]", sysUser);
        return ResponseDTO.returnSuccess("操作成功", sysUser);
    }

}
