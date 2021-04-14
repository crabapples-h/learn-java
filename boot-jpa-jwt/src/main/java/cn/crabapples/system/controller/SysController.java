package cn.crabapples.system.controller;

import cn.crabapples.common.Groups;
import cn.crabapples.common.PageDTO;
import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.dto.ResponseDTO;
import cn.crabapples.common.utils.jwt.JwtIgnore;
import cn.crabapples.system.dto.SysRolesDTO;
import cn.crabapples.system.entity.SysMenus;
import cn.crabapples.system.entity.SysRoles;
import cn.crabapples.system.form.MenusForm;
import cn.crabapples.system.form.RolesForm;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.SysService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
@Api("系统管理")
@Slf4j
@RequestMapping("/api/sys/")
public class SysController extends BaseController {

    private final SysService sysService;

    public SysController(SysService sysService) {
        this.sysService = sysService;
    }

    /**
     * 测试接口
     */
    @JwtIgnore
    @PostMapping("/test/{id}")
    @ApiOperation(value = "测试接口", notes = "测试接口")
    public ResponseDTO test(@PathVariable String id) {
        log.info("收到请求->测试接口:[{}]", id);
        log.info("返回结果->测试接口");
        return ResponseDTO.returnSuccess("测试");
    }

    /**
     * 发起登录请求
     *
     * @param form 用户名和密码
     * @return 登录成功返回token
     */
    @JwtIgnore
    @PostMapping("/login")
    @ApiOperation(value = "用户登陆", notes = "用户登陆接口")
    public ResponseDTO loginCheck(@RequestBody UserForm form) {
        log.info("收到请求->用户登陆验证:[{}]", form);
        super.validator(form, Groups.IsLogin.class);
        String token = sysService.loginCheck(form);
        log.info("返回结果->登录成功->token:[{}]", token);
        return ResponseDTO.returnSuccess("登录成功", token);
    }

    /**
     * 获取系统菜单
     *
     * @return 返回当前用户拥有的系统菜单
     */
    @GetMapping("/user/menus")
    @ResponseBody
    public ResponseDTO getUserMenus(HttpServletRequest request) {
        log.info("收到请求->获取用户菜单列表");
        List<SysMenus> menus = sysService.getUserMenus(request);
        log.info("返回结果->获取菜单列表成功:[{}]", menus);
        return ResponseDTO.returnSuccess(menus);
    }


    @GetMapping("/menus/list")
    @ResponseBody
    public ResponseDTO getMenusList(HttpServletRequest request, PageDTO page) {
        log.info("收到请求->获取所有菜单列表");
        List<SysMenus> list = sysService.getMenusList(request, page);
        log.info("返回结果->获取菜单列表成功:[{}]", list);
        return ResponseDTO.returnSuccess(list, page);
    }

    @PostMapping("/menus/save")
    @ResponseBody
    public ResponseDTO saveMenus(HttpServletRequest request, @RequestBody MenusForm form) {
        log.info("收到请求->保存菜单:[{}]", form);
        sysService.saveMenus(form);
        log.info("返回结果->保存菜单成功");
        return ResponseDTO.returnSuccess();
    }

    @PostMapping("/menus/remove/{id}")
    @ResponseBody
    public ResponseDTO removeMenus(HttpServletRequest request, @PathVariable String id) {
        log.info("收到请求->删除菜单:[{}]", id);
        sysService.removeMenus(id);
        log.info("返回结果->删除菜单成功");
        return ResponseDTO.returnSuccess();
    }

    @GetMapping("/roles")
    @ResponseBody
    public ResponseDTO getUserRolesList(HttpServletRequest request) {
        log.info("收到请求->获取用户角色列表");
        List<SysRolesDTO> list = sysService.getUserRolesList(request);
        log.info("返回结果->获取菜单列表成功:[{}]", list);
        return ResponseDTO.returnSuccess(list);
    }

    @GetMapping("/roles/list")
    @ResponseBody
    public ResponseDTO getRolesList(HttpServletRequest request, PageDTO page) throws IOException {
        log.info("收到请求->获取所有角色列表");
        List<SysRoles> list = sysService.getRolesList(request, page);
        log.info("返回结果->获取菜单列表成功:[{}]", list);
        return ResponseDTO.returnSuccess(list, page);
    }

    @PostMapping("/roles/save")
    @ResponseBody
    public ResponseDTO saveRoles(HttpServletRequest request, @RequestBody RolesForm form) {
        log.info("收到请求->保存角色:[{}]", form);
        sysService.saveRoles(form);
        log.info("返回结果->保存角色成功");
        return ResponseDTO.returnSuccess();
    }

    @PostMapping("/roles/remove/{id}")
    @ResponseBody
    public ResponseDTO removeRoles(HttpServletRequest request, @PathVariable String id) {
        log.info("收到请求->删除角色:[{}]", id);
        sysService.removeRoles(id);
        log.info("返回结果->删除角色成功");
        return ResponseDTO.returnSuccess();
    }

}
