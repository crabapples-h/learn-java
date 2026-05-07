package cn.crabapples.system.sysRole.controller;

import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.base.ResponseDTO;
import cn.crabapples.system.dto.SysRolesDTO;
import cn.crabapples.system.sysRole.entity.SysRole;
import cn.crabapples.system.sysRole.form.RolesForm;
import cn.crabapples.system.sysRole.service.SystemRolesService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
@Tag(name = "系统接口[角色]")
@Slf4j
@RequestMapping("/api/system/role")
public class SystemRoleController extends BaseController {

    private final SystemRolesService rolesService;

    public SystemRoleController(SystemRolesService rolesService) {
        this.rolesService = rolesService;
    }

    /**
     * 获取[当前用户]角色列表
     */
    @GetMapping("/user")
    @Operation(summary = "获取当前用户角色列表", description = "获取当前用户角色列表")
    public ResponseDTO<List<SysRolesDTO>> getUserRoles() {
        log.info("收到请求->获取[当前用户]角色列表");
        List<SysRolesDTO> list = rolesService.getUserRolesDTO();
        log.debug("返回结果->获取[当前用户]角色列表成功:[{}]", list);
        return new ResponseDTO<>(list);
    }

    /**
     * 获取[指定用户]角色列表
     */
    @GetMapping("/user/{id}")
    @Operation(summary = "获取用户角色列表", description = "获取用户角色列表")
    public ResponseDTO<List<SysRolesDTO>> getUserRolesById(@PathVariable String id) {
        log.info("收到请求->获取用户角色列表:[{}]", id);
        List<SysRolesDTO> list = rolesService.getUserRolesById(id);
        log.debug("返回结果->获取[指定用户]角色列表成功:[{}]", list);
        return new ResponseDTO<>(list);
    }

    /**
     * 获取角色列表
     */
    @GetMapping("/page")
    @Operation(summary = "获取角色分页列表", description = "获取角色分页列表")
    public ResponseDTO<Page<SysRole>> getRolesPage(
            @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            RolesForm form) {
        log.info("收到请求->获取角色[分页]列表");
        Page<SysRole> list = rolesService.getRolesPage(pageIndex, pageSize, form);
        log.debug("返回结果->获取角色[分页]列表成功:[{}]", list);
        return new ResponseDTO<>(list);
    }

    /**
     * 获取角色列表
     */
    @GetMapping("/list")
    @Operation(summary = "获取用户角色不分页列表", description = "获取用户角色不分页列表")
    public ResponseDTO<List<SysRole>> getRolesList(RolesForm form) {
        log.info("收到请求->获取角色列表");
        List<SysRole> list = rolesService.getRolesList(form);
        log.debug("返回结果->获取角色列表成功:[{}]", list);
        return new ResponseDTO<>(list);
    }

    /**
     * 保存角色
     */
    @PostMapping("/save")
    @Operation(summary = "保存角色", description = "保存角色")
    public ResponseDTO<Object> saveRoles(@RequestBody RolesForm form) {
        log.info("收到请求->保存角色:[{}]", form);
        rolesService.saveRoles(form);
        log.info("返回结果->保存角色成功");
        return new ResponseDTO<>().returnSuccess("保存成功");
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/remove/{id}")
    @Operation(summary = "删除角色", description = "删除角色")
    public ResponseDTO<Object> removeRoles(@PathVariable String id) {
        log.info("收到请求->删除角色:[{}]", id);
        rolesService.removeRoles(id);
        log.info("返回结果->删除角色成功");
        return new ResponseDTO<>().returnSuccess("删除成功");
    }

}
