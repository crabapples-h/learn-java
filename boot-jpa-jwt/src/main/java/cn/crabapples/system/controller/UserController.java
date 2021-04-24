package cn.crabapples.system.controller;

import cn.crabapples.common.Groups;
import cn.crabapples.common.PageDTO;
import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.dto.ResponseDTO;
import cn.crabapples.system.dto.SysUserDTO;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


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
@Slf4j
public class UserController extends BaseController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加用户", notes = "添加用户接口")
    public ResponseDTO addUser(@RequestBody UserForm form) {
        log.info("收到请求->添加用户:[{}]", form);
        super.validator(form, Groups.IsAdd.class);
        SysUser entity = userService.addUser(form);
        log.info("返回结果->用户添加完成:[{}]", entity);
        return ResponseDTO.returnSuccess(entity);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑用户", notes = "编辑用户接口")
    public ResponseDTO editUser(@RequestBody UserForm form) {
        log.info("收到请求->编辑用户:[{}]", form);
        super.validator(form, Groups.IsEdit.class);
        SysUser entity = userService.editUser(form);
        log.info("返回结果->用户编辑完成:[{}]", entity);
        return ResponseDTO.returnSuccess(entity);
    }

    @PostMapping("/del/{id}")
    @ApiOperation(value = "删除用户", notes = "删除用户接口")
    public ResponseDTO delUser(@PathVariable String id) {
        log.info("收到请求->删除用户:[{}]", id);
        userService.delUser(id);
        log.info("返回结果->用户删除完成");
        return ResponseDTO.returnSuccess();
    }

    @PostMapping("/lock/{id}")
    @ApiOperation(value = "锁定用户", notes = "锁定用户接口")
    public ResponseDTO lockUser(@PathVariable String id) {
        log.info("收到请求->锁定用户,id:[{}]", id);
        userService.lockUser(id);
        log.info("返回结果->锁定用户完成");
        return ResponseDTO.returnSuccess();
    }

    @PostMapping("/unlock/{id}")
    @ApiOperation(value = "解锁用户", notes = "锁定用户接口")
    public ResponseDTO unlockUser(@PathVariable String id) {
        log.info("收到请求->解锁用户,id:[{}]", id);
        userService.unlockUser(id);
        log.info("返回结果->解锁用户完成");
        return ResponseDTO.returnSuccess();
    }

    @PostMapping("/password/reset")
    @ApiOperation(value = "重置密码", notes = "重置密码接口")
    public ResponseDTO resetPassword(@RequestBody UserForm form) {
        log.info("收到请求->重置密码:[{}]", form);
        super.validator(form, Groups.IsResetPassword.class);
        userService.resetPassword(form);
        log.info("返回结果->重置密码完成");
        return ResponseDTO.returnSuccess();
    }

    @PostMapping("/password/update")
    @ApiOperation(value = "修改密码", notes = "修改密码接口")
    public ResponseDTO updatePassword(@RequestBody UserForm form) {
        log.info("收到请求->修改密码:[{}]", form);
        super.validator(form, Groups.IsUpdatePassword.class);
        userService.updatePassword(form);
        log.info("返回结果->修改密码完成");
        return ResponseDTO.returnSuccess();
    }

    @GetMapping("/info")
    @ApiOperation(value = "获取当前用户信息", notes = "获取当前用户信息接口")
    public ResponseDTO getUserInfo(HttpServletRequest request) {
        log.info("收到请求->获取当前用户信息");
        SysUser entity = userService.getUserInfo(request);
        log.info("返回结果->获取当前用户信息结束:[{}]", entity);
        return ResponseDTO.returnSuccess(entity);
    }

    @GetMapping("/list/page")
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表接口")
    public ResponseDTO getUserListPage(HttpServletRequest request, PageDTO page) {
        log.info("收到请求->获取用户列表");
        List<SysUserDTO> list = userService.getUserListPage(request, page);
        log.info("返回结果->获取当前用户信息结束:[{}]", list);
        return ResponseDTO.returnSuccess(list, page);
    }

}
