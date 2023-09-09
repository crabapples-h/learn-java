package cn.crabapples.system.controller;

import cn.crabapples.common.Groups;
import cn.crabapples.common.ResponseDTO;
import cn.crabapples.common.base.BaseController;
import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.form.MenusForm;
import cn.crabapples.system.service.SystemMenusService;
import cn.crabapples.system.service.SystemRoleMenusService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO 系统菜单相关接口
 *
 * @author Mr.He
 * 2021/4/24 23:57
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */

@RestController
@Api("系统接口[菜单]")
@Slf4j
@RequestMapping("/api/system/menu")
public class SystemMenuController extends BaseController {

    private final SystemMenusService menusService;
    private final SystemRoleMenusService roleMenusService;

    public SystemMenuController(SystemMenusService menusService, SystemRoleMenusService roleMenusService) {
        this.menusService = menusService;
        this.roleMenusService = roleMenusService;
    }

    /**
     * 获取[当前用户]菜单树
     */
    @GetMapping("/user")
    public ResponseDTO getUserMenus() {
        log.info("收到请求->获取[当前用户]菜单树");
        List<SysMenu> menus = menusService.getUserMenusTree();
        log.debug("返回结果->获取[当前用户]菜单树:[{}]", menus);
        return ResponseDTO.returnSuccess(menus);
    }

    /**
     * 获取[所有]菜单列表
     */
    @GetMapping("/list")
    public ResponseDTO getMenusList() {
        log.info("收到请求->获取[所有]菜单列表");
        List<SysMenu> list = menusService.getMenusList();
        log.debug("返回结果->获取[所有]菜单列表成功:[{}]", list);
        return ResponseDTO.returnSuccess(list);
    }


    /**
     * 获取[角色]菜单列表
     */
    @GetMapping("/role/{id}")
    public ResponseDTO getRoleMenus(@PathVariable String id) {
        log.info("收到请求->获取[角色]菜单列表:[{}]", id);
        List<SysMenu> list = roleMenusService.getRoleMenusList(id);
        log.debug("返回结果->获取[角色]菜单列表成功:[{}]", list);
        return ResponseDTO.returnSuccess(list);
    }

    /**
     * 保存菜单
     */
    @PostMapping("/save")
    public ResponseDTO saveMenus(@RequestBody MenusForm form) {
        super.validator(form, Groups.IsAdd.class, Groups.IsEdit.class);
        log.info("收到请求->保存菜单:[{}]", form);
        boolean status = menusService.saveMenus(form);
        log.debug("返回结果->保存菜单成功:[{}]", status);
        return ResponseDTO.returnSuccess();
    }

    /**
     * 删除菜单
     */
    @PostMapping("/remove/{id}")
    public ResponseDTO removeMenus(@PathVariable String id) {
        log.info("收到请求->删除菜单:[{}]", id);
        boolean status = menusService.removeMenus(id);
        log.debug("返回结果->删除菜单成功:[{}]", status);
        return ResponseDTO.returnSuccess();
    }

}
