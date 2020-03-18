package cn.crabapples.spring.controller;

import cn.crabapples.spring.common.BaseController;
import cn.crabapples.spring.dto.ResponseDTO;
import cn.crabapples.spring.entity.MenuInfo;
import cn.crabapples.spring.entity.MenuList;
import cn.crabapples.spring.service.MenuInfoService;
import cn.crabapples.spring.service.MenuListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * TODO
 *
 * @author Mr.He
 * 2020/3/18 23:36
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Controller
@ResponseBody
@RequestMapping(value = "/api/menu")
@Api("用户管理")
public class MenusController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(MenusController.class);
    private final MenuListService menuListService;
    private final MenuInfoService menuInfoService;

    public MenusController(MenuListService menuListService, MenuInfoService menuInfoService) {
        this.menuListService = menuListService;
        this.menuInfoService = menuInfoService;
    }


    @RequestMapping("/getMenuList")
    @ApiOperation(value = "获取菜单列表", notes = "获取菜单列表")
    public ResponseDTO getMenuList(){
        logger.info("收到请求->获取菜单列表:[{}]");
//        super.validator(form, IsAdd.class);
        List<MenuList> menuLists = menuListService.findAll();
        logger.info("获取菜单列表->菜单列表信息:[{}]",menuLists);
        return ResponseDTO.returnSuccess("操作成功",menuLists);
    }

    @RequestMapping("/getMenuInfo/{id}")
    @ApiOperation(value = "获取菜单详情", notes = "获取菜单详情")
    public ResponseDTO getMenuInfo(@PathVariable String id){
        logger.info("收到请求->获取菜单详情:[{}]", id);
        List<MenuInfo> menuInfos = menuInfoService.findByListId(id);
        logger.info("获取菜单详情->菜单详情信息:[{}]",menuInfos);
        return ResponseDTO.returnSuccess("操作成功",menuInfos);
    }

//    @PostMapping("/submit")
//    @ApiOperation(value = "修改用户", notes = "修改用户接口")
//    public ResponseDTO submit(@RequestBody UserForm form){
//        logger.info("收到请求->修改用户:[{}]",form);
//        super.validator(form, IsEdit.class);
//        SysUser user = userService.editUser(form);
//        logger.info("用户修改完成->用户信息:[{}]",user);
//        return ResponseDTO.returnSuccess("操作成功",user);
//    }
//
//    @PostMapping("/delUser")
//    @ApiOperation(value = "删除用户", notes = "删除用户接口")
//    public ResponseDTO delUser(@RequestBody UserForm form){
//        super.validator(form, IsStatusChange.class);
//        logger.info("收到请求->删除用户:[{}]",form.getId());
//        userService.delUser(form.getId());
//        logger.info("用户删除完成");
//        return ResponseDTO.returnSuccess("操作成功");
//    }
//
//    @PostMapping("/unActiveUser")
//    @ApiOperation(value = "禁用用户", notes = "禁用用户接口")
//    public ResponseDTO unActiveUser(@RequestBody UserForm form){
//        super.validator(form, IsStatusChange.class);
//        logger.info("收到请求->禁用用户:[{}]",form.getId());
//        userService.unActiveUser(form.getId());
//        logger.info("用户禁用完成");
//        return ResponseDTO.returnSuccess("操作成功");
//    }
//
//    @PostMapping("/activeUser")
//    @ApiOperation(value = "激活用户", notes = "激活用户接口")
//    public ResponseDTO activeUser(@RequestBody UserForm form){
//        super.validator(form, IsStatusChange.class);
//        logger.info("收到请求->激活用户:[{}]",form.getId());
//        userService.activeUser(form.getId());
//        logger.info("用户激活完成");
//        return ResponseDTO.returnSuccess("操作成功");
//    }

}
