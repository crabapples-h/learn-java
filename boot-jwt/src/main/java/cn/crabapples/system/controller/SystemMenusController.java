package cn.crabapples.system.controller;

import cn.crabapples.common.Groups;
import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.ResponseDTO;
import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.form.MenusForm;
import cn.crabapples.system.service.SystemMenusService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
@Api("系统管理[菜单]")
@Slf4j
@RequestMapping("/api/system/menus")
public class SystemMenusController extends BaseController {

    private final SystemMenusService menusService;

    public SystemMenusController(SystemMenusService menusService) {
        this.menusService = menusService;
    }

    /**
     * 获取[所有]菜单列表
     */
    @GetMapping("/list/user")
    public ResponseDTO getByUser() {
        log.info("收到请求->获取[用户]菜单列表");
        List<SysMenu> list = menusService.getByUser();
        log.info("返回结果->获取[所有]菜单列表成功:[{}]", list);
        return ResponseDTO.returnSuccess(list);
    }

    /**
     * 获取[所有]菜单列表
     */
    @GetMapping("/list")
    public ResponseDTO getList(@RequestBody(required = false) MenusForm form) {
        log.info("收到请求->获取[所有]菜单列表");
        List<SysMenu> list = menusService.getList(form);
        log.info("返回结果->获取[所有]菜单列表成功:[{}]", list);
        return ResponseDTO.returnSuccess(list);
    }

    /**
     * 获取[分页]菜单列表
     */
    @GetMapping("/page")
    public ResponseDTO getPage(MenusForm form) {
        log.info("收到请求->获取[分页]菜单列表");
        Page<SysMenu> page = menusService.getPage(form);
        log.info("返回结果->获取[分页]菜单列表成功:[{}]", page);
        return ResponseDTO.returnSuccess(page);
    }

    /**
     * 保存菜单
     */
    @PostMapping("/save")
    public ResponseDTO save(@RequestBody MenusForm form) {
        super.validator(form, Groups.IsAdd.class, Groups.IsEdit.class);
        log.info("收到请求->保存菜单:[{}]", form);
        menusService.save(form);
        log.info("返回结果->保存菜单成功");
        return ResponseDTO.returnSuccess();
    }

    /**
     * 删除菜单-逻辑
     */
    @PostMapping("/delete/{id}")
    public ResponseDTO delete(@PathVariable String id) {
        log.info("收到请求->[逻辑]删除菜单:[{}]", id);
        menusService.delete(id);
        log.info("返回结果->[逻辑]删除菜单成功");
        return ResponseDTO.returnSuccess();
    }

    /**
     * 删除菜单-物理(待测试)
     */
    @PostMapping("/delete/really/{id}")
    public ResponseDTO deleteReally(@PathVariable String id) {
        log.info("收到请求->[物理]删除菜单:[{}]", id);
        menusService.deleteReally(id);
        log.info("返回结果->[物理]删除菜单成功");
        return ResponseDTO.returnSuccess();
    }

}
