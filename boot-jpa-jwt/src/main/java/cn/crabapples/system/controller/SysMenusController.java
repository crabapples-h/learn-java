package cn.crabapples.system.controller;

import cn.crabapples.common.Groups;
import cn.crabapples.common.PageDTO;
import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.dto.ResponseDTO;
import cn.crabapples.system.entity.SysMenus;
import cn.crabapples.system.form.MenusForm;
import cn.crabapples.system.service.SysMenusService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@Api("系统管理[菜单]")
@Slf4j
@RequestMapping("/api/sys/menus")
public class SysMenusController extends BaseController {

    private final SysMenusService menusService;

    public SysMenusController(SysMenusService menusService) {
        this.menusService = menusService;
    }

    /**
     * 获取[当前用户]菜单列表
     */
    @GetMapping("/user")
    public ResponseDTO getUserMenus(HttpServletRequest request) {
        log.info("收到请求->获取[当前用户]菜单列表");
        List<SysMenus> menus = menusService.getUserMenus(request);
        log.info("返回结果->获取[当前用户]菜单列表成功:[{}]", menus);
        return ResponseDTO.returnSuccess(menus);
    }

    /**
     * 获取[所有]菜单列表
     */
    @GetMapping("/list")
    public ResponseDTO getMenusList(HttpServletRequest request) {
        log.info("收到请求->获取[所有]菜单列表");
        List<SysMenus> list = menusService.getMenusList(request);
        log.info("返回结果->获取[所有]菜单列表成功:[{}]", list);
        return ResponseDTO.returnSuccess(list);
    }

    /**
     * 获取[分页]菜单列表
     */
    @GetMapping("/page")
    public ResponseDTO getMenusPage(HttpServletRequest request, PageDTO page) {
        log.info("收到请求->获取[分页]菜单列表");
        List<SysMenus> list = menusService.getMenusPage(request, page);
        log.info("返回结果->获取[分页]菜单列表成功:[{}]", list);
        return ResponseDTO.returnSuccess(list, page);
    }

    /**
     * 保存菜单
     */
    @PostMapping("/save")
    public ResponseDTO saveMenus(HttpServletRequest request, @RequestBody MenusForm form) {
        super.validator(form, Groups.IsAdd.class, Groups.IsEdit.class);
        log.info("收到请求->保存菜单:[{}]", form);
        menusService.saveMenus(form);
        log.info("返回结果->保存菜单成功");
        return ResponseDTO.returnSuccess();
    }

    /**
     * 删除菜单
     */
    @PostMapping("/remove/{id}")
    public ResponseDTO removeMenus(HttpServletRequest request, @PathVariable String id) {
        log.info("收到请求->删除菜单:[{}]", id);
        menusService.removeMenus(id);
        log.info("返回结果->删除菜单成功");
        return ResponseDTO.returnSuccess();
    }

}
