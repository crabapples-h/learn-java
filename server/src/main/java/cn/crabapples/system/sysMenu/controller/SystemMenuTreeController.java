package cn.crabapples.system.sysMenu.controller;

import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.base.ResponseDTO;
import cn.crabapples.system.sysMenu.entity.SysMenu;
import cn.crabapples.system.sysMenu.form.MenusForm;
import cn.crabapples.system.sysMenu.service.SystemMenusService;
import cn.crabapples.system.sysRoleMenu.service.SystemRoleMenusService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
@RequestMapping("/api/system/menu/tree")
public class SystemMenuTreeController extends BaseController {

    private final SystemMenusService menusService;
    private final SystemRoleMenusService roleMenusService;

    public SystemMenuTreeController(SystemMenusService menusService, SystemRoleMenusService roleMenusService) {
        this.menusService = menusService;
        this.roleMenusService = roleMenusService;
    }

    /**
     * 获取[当前用户]菜单树
     * @return 菜单树
     */
    @GetMapping("/user")
    @Operation(summary = "获取当前用户菜单树", description = "获取当前用户菜单树接口")
    public ResponseDTO<List<SysMenu>> getUserMenus() {
        log.info("收到请求->获取[当前用户]菜单树");
        List<SysMenu> menus = menusService.getUserMenusTree();
        log.debug("返回结果->获取[当前用户]菜单树:[{}]", menus);
        return new ResponseDTO<>(menus);
    }

    /**
     * 获取[所有]菜单树
     * @return 菜单树
     */
    @GetMapping("/list")
    @Operation(summary = "获取所有菜单树", description = "获取所有菜单树接口")
    public ResponseDTO<List<SysMenu>> getMenusList() {
        log.info("收到请求->获取[所有]菜单树");
        List<SysMenu> list = menusService.getMenusTreeList();
        log.debug("返回结果->获取[所有]菜单树成功:[{}]", list);
        return new ResponseDTO<>(list);
    }

    /**
     * 获取[分页]菜单树
     * @param pageIndex 页码
     * @param pageSize 每页数量
     * @param form 查询条件
     * @return 菜单树
     */
    @GetMapping("/page")
    @Operation(summary = "获取分页菜单树", description = "获取所有分页树接口")
    public ResponseDTO<Page<SysMenu>> getMenuPage(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                                  @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                  MenusForm form) {
        log.info("收到请求->获取[分页]菜单树");
        Page<SysMenu> list = menusService.getMenuTreePage(pageIndex, pageSize, form);
        log.debug("返回结果->获取[分页]菜单树成功:[{}]", list);
        return new ResponseDTO<>(list);
    }


    /**
     * 获取[角色]菜单树
     * @param id 角色id
     * @return 菜单树
     */
    @GetMapping("/role/{id}")
    @Operation(summary = "获取角色菜单树", description = "获取角色菜单列表树")
    public ResponseDTO<List<SysMenu>> getRoleMenusTree(@PathVariable String id) {
        log.info("收到请求->获取[角色]菜单列表(tree):[{}]", id);
        List<SysMenu> list = roleMenusService.getRoleMenusTree(id);
        log.debug("返回结果->获取[角色]菜单列表(tree)成功:[{}]", list);
        return new ResponseDTO<>(list);
    }
}
