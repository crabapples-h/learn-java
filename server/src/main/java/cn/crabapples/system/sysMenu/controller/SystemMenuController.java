package cn.crabapples.system.sysMenu.controller;

import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.base.ResponseDTO;
import cn.crabapples.common.utils.Groups;
import cn.crabapples.system.sysMenu.entity.SysMenu;
import cn.crabapples.system.sysMenu.form.MenusForm;
import cn.crabapples.system.sysMenu.service.SystemMenusService;
import cn.crabapples.system.sysRoleMenu.service.SystemRoleMenusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "系统接口[菜单]")
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
     * 获取子菜单列表
     *
     * @param pid 父级id
     * @return 子菜单列表
     */
    @GetMapping("/child/list")
    @Operation(summary = "获取子菜单列表", description = "获取子菜单列表")
    public ResponseDTO<List<SysMenu>> getChildList(String pid) {
        log.info("收到请求->获取获取子菜单列表");
        List<SysMenu> menus = menusService.getChildList(pid);
        log.debug("返回结果->获取获取子菜单列表:[{}]", menus);
        return new ResponseDTO<>(menus);
    }


    /**
     * 保存菜单
     *
     * @param form 菜单表单
     * @return 保存结果
     */
    @PostMapping("/save")
    public ResponseDTO<Boolean> saveMenus(@RequestBody MenusForm form) {
        super.validator(form, Groups.IsAdd.class, Groups.IsEdit.class);
        log.info("收到请求->保存菜单:[{}]", form);
        boolean status = menusService.saveMenus(form);
        log.debug("返回结果->保存菜单成功:[{}]", status);
        return new ResponseDTO<>(status);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单id
     * @return 删除结果
     */
    @DeleteMapping("/remove/{id}")
    @Operation(summary = "保存菜单", description = "保存菜单")
    public ResponseDTO<Boolean> removeMenus(@PathVariable String id) {
        log.info("收到请求->删除菜单:[{}]", id);
        boolean status = menusService.removeMenus(id);
        log.debug("返回结果->删除菜单成功:[{}]", status);
        return new ResponseDTO<>(status);
    }

}
