package cn.crabapples.system.controller;

import cn.crabapples.common.Groups;
import cn.crabapples.common.ResponseDTO;
import cn.crabapples.common.base.BaseController;
import cn.crabapples.system.dto.SysUserDTO;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.SystemUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("系统接口[用户]")
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
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表接口")
    public ResponseDTO getUserPage(
            @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            UserForm form) {
        log.info("收到请求->获取用户列表:[{}]", form);
        IPage<SysUserDTO> list = userService.findAll(pageIndex, pageSize, form);
        log.debug("返回结果->获取[分页]用户列表->完成:[{}]", list);
        return ResponseDTO.returnSuccess(list);
    }

    /**
     * 获取[不分页]用户列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表接口")
    public ResponseDTO getUserList(UserForm form) {
        log.info("收到请求->获取用户列表:[{}]", form);
        List<SysUserDTO> list = userService.findAll(form);
        log.debug("返回结果->获取[分页]用户列表->完成:[{}]", list);
        return ResponseDTO.returnSuccess(list);
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存用户", notes = "保存用户接口")
    public ResponseDTO saveUser(@RequestBody UserForm form) {
        log.info("收到请求->保存用户:[{}]", form);
        super.validator(form, Groups.IsAdd.class);
        boolean status = userService.saveUser(form);
        log.debug("返回结果->添加用户->完成:[{}]", status);
        return ResponseDTO.returnSuccess(status);
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
    public ResponseDTO resetPassword(@RequestBody UserForm.ResetPassword form) {
        log.info("收到请求->重置密码:[{}]", form);
        super.validator(form, Groups.IsResetPassword.class);
        userService.resetPassword(form);
        log.info("返回结果->重置密码完成");
        return ResponseDTO.returnSuccess();
    }

    @PostMapping("/password/update")
    @ApiOperation(value = "修改密码", notes = "修改密码接口")
    public ResponseDTO updatePassword(@RequestBody UserForm.ResetPassword form) {
        log.info("收到请求->修改密码:[{}]", form);
        super.validator(form, Groups.IsUpdatePassword.class);
        userService.updatePassword(form);
        log.info("返回结果->修改密码完成");
        return ResponseDTO.returnSuccess();
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据Id获取用户", notes = "根据Id获取用户接口")
    public ResponseDTO getById(@PathVariable String id) {
        log.info("收到请求->根据Id获取用户:[{}]", id);
        SysUserDTO entity = userService.getById(id);
        log.info("返回结果-根据Id获取用户完成");
        return ResponseDTO.returnSuccess(entity);
    }


}
