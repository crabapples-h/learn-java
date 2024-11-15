package cn.crabapples.system.sysUser.controller;

import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.base.ResponseDTO;
import cn.crabapples.common.dic.EnableDict;
import cn.crabapples.common.utils.Groups;
import cn.crabapples.system.dto.SysUserDTO;
import cn.crabapples.system.sysUser.form.ResetPasswordForm;
import cn.crabapples.system.sysUser.form.UpdatePasswordForm;
import cn.crabapples.system.sysUser.form.UserForm;
import cn.crabapples.system.sysUser.service.SystemUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO 系统用户相关接口
 *
 * @author Mr.He
 * 2021/4/24 23:58
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@RestController
@RequestMapping(value = "/api/system/user")
//@Api("系统接口[用户]")
@Slf4j
public class SystemUserController extends BaseController {
    private final SystemUserService userService;

    public SystemUserController(SystemUserService userService) {
        this.userService = userService;
    }

    /**
     * 获取[分页]用户列表
     */
    @GetMapping("/page")
//    @ApiOperation(value = "获取用户列表", notes = "获取用户列表接口")
    @EnableDict
    public ResponseDTO<IPage<SysUserDTO>> getUserPage(
            @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            UserForm form) {
        log.info("收到请求->获取用户[分页]列表:[{}]", form);
        IPage<SysUserDTO> list = userService.findAll(pageIndex, pageSize, form);
        log.debug("返回结果->获取[分页]用户列表->完成:[{}]", list);
        return new ResponseDTO<>(list);
    }

    /**
     * 获取[不分页]用户列表
     */
    @GetMapping("/list")
//    @ApiOperation(value = "获取用户列表", notes = "获取用户列表接口")
    public ResponseDTO<List<SysUserDTO>> getUserList(UserForm form) {
        log.info("收到请求->获取用户[不分页]列表:[{}]", form);
        List<SysUserDTO> list = userService.findAll(form);
        log.debug("返回结果->获取[不分页]用户列表->完成:[{}]", list);
        return new ResponseDTO<>(list);
    }

    @PostMapping("/save")
//    @ApiOperation(value = "保存用户", notes = "保存用户接口")
    public ResponseDTO<Boolean> saveUser(@RequestBody UserForm form) {
        log.info("收到请求->保存用户:[{}]", form);
        super.validator(form, Groups.IsAdd.class);
        boolean status = userService.saveUser(form);
        log.debug("返回结果->添加用户->完成:[{}]", status);
        return new ResponseDTO<>(status).returnSuccess("操作成功");
    }

    @DeleteMapping("/remove/{id}")
//    @ApiOperation(value = "删除用户", notes = "删除用户接口")
    public ResponseDTO<Boolean> removeUser(@PathVariable String id) {
        log.info("收到请求->删除用户:[{}]", id);
        boolean status = userService.removeUser(id);
        log.info("返回结果->用户删除完成");
        return new ResponseDTO<>(status);
    }

    @PostMapping("/lock/{id}")
//    @ApiOperation(value = "锁定用户", notes = "锁定用户接口")
    public ResponseDTO<Boolean> lockUser(@PathVariable String id) {
        log.info("收到请求->锁定用户,id:[{}]", id);
        boolean status = userService.lockUser(id);
        log.info("返回结果->锁定用户完成");
        return new ResponseDTO<>(status);
    }

    @PostMapping("/unlock/{id}")
//    @ApiOperation(value = "解锁用户", notes = "锁定用户接口")
    public ResponseDTO<Boolean> unlockUser(@PathVariable String id) {
        log.info("收到请求->解锁用户,id:[{}]", id);
        boolean status = userService.unlockUser(id);
        log.info("返回结果->解锁用户完成");
        return new ResponseDTO<>(status);
    }

    @PostMapping("/password/reset")
//    @ApiOperation(value = "重置密码", notes = "重置密码接口")
    public ResponseDTO<Boolean> resetPassword(@RequestBody ResetPasswordForm form) {
        log.info("收到请求->重置密码:[{}]", form);
        super.validator(form, Groups.IsResetPassword.class);
        boolean status = userService.resetPassword(form);
        log.info("返回结果->重置密码完成");
        return new ResponseDTO<>(status);
    }

    @PostMapping("/password/update")
//    @ApiOperation(value = "修改密码", notes = "修改密码接口")
    public ResponseDTO<Boolean> updatePassword(@RequestBody UpdatePasswordForm form) {
        log.info("收到请求->修改密码:[{}]", form);
        super.validator(form, Groups.IsUpdatePassword.class);
        boolean status = userService.updatePassword(form);
        log.info("返回结果->修改密码完成");
        return new ResponseDTO<>(status);
    }

    @GetMapping("/get/{id}")
//    @ApiOperation(value = "根据Id获取用户", notes = "根据Id获取用户接口")
    public ResponseDTO<SysUserDTO> getById(@PathVariable String id) {
        log.info("收到请求->根据Id获取用户:[{}]", id);
        SysUserDTO entity = userService.getById(id);
        log.info("返回结果-根据Id获取用户完成");
        return new ResponseDTO<>(entity);
    }


}
