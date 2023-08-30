package cn.crabapples.system.controller;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.PageDTO;
import cn.crabapples.common.ResponseDTO;
import cn.crabapples.common.base.BaseController;
import cn.crabapples.system.dto.SysRolesDTO;
import cn.crabapples.system.entity.SysRoles;
import cn.crabapples.system.form.RolesForm;
import cn.crabapples.system.service.SystemRolesService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * TODO 系统角色接口
 *
 * @author Mr.He
 * 2021/4/24 23:56
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@RestController
@Api("系统接口[角色]")
@Slf4j
@RequestMapping("/api/system/roles")
public class SystemRolesController extends BaseController {

    private final SystemRolesService rolesService;

    public SystemRolesController(SystemRolesService rolesService) {
        this.rolesService = rolesService;
    }

    /**
     * 获取[当前用户]角色列表
     */
    @GetMapping("/user")
    public ResponseDTO getUserRoles() {
        log.info("收到请求->获取[当前用户]角色列表");
//        throw new ApplicationException("暂未实现");
        List<SysRolesDTO> list = rolesService.getUserRoles();
        log.info("返回结果->获取[当前用户]角色列表成功:[{}]", list);
        return ResponseDTO.returnSuccess(list);
    }

    /**
     * 获取角色列表
     */
    @GetMapping("/list")
    public ResponseDTO getRolesPage(@RequestParam(name = "pageIndex", required = false) Integer pageIndex,
                                    @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                    RolesForm form) {
        log.info("收到请求->获取角色列表");
        Iterable<SysRoles> list = rolesService.getRolesList(pageIndex, pageSize, form);
        log.info("返回结果->获取角色列表成功:[{}]", list);
        return ResponseDTO.returnSuccess(list);
    }

    /**
     * 保存角色
     */
    @PostMapping("/save")
    public ResponseDTO saveRoles(@RequestBody RolesForm form) {
        log.info("收到请求->保存角色:[{}]", form);
        rolesService.saveRoles(form);
        log.info("返回结果->保存角色成功");
        return ResponseDTO.returnSuccess();
    }

    /**
     * 删除角色
     */
    @PostMapping("/remove/{id}")
    public ResponseDTO removeRoles(HttpServletRequest request, @PathVariable String id) {
        log.info("收到请求->删除角色:[{}]", id);
        rolesService.removeRoles(id);
        log.info("返回结果->删除角色成功");
        return ResponseDTO.returnSuccess();
    }

}
