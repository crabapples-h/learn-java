package cn.crabapples.system.sysDepart.contoller;

import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.base.ResponseDTO;
import cn.crabapples.system.sysDepart.entity.SysDepart;
import cn.crabapples.system.sysDepart.form.DepartForm;
import cn.crabapples.system.sysDepart.service.SystemDepartService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "系统接口[部门]")
@Slf4j
@RequestMapping("/api/system/depart")
public class SystemDepartController extends BaseController {

    private final SystemDepartService dictService;

    public SystemDepartController(SystemDepartService dictService) {
        this.dictService = dictService;
    }


    @GetMapping("/page")
    @Operation(summary = "系统部门", description = "系统部门接口")
    public ResponseDTO<Page<SysDepart>> page(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                             @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                             DepartForm form) {
        log.info("收到请求->获取[分页]系统部门:[{}]", form);
        Page<SysDepart> list = dictService.page(pageIndex, pageSize, form);
        log.debug("返回结果->获取[分页]系统部门完成:[{}]", list);
        return new ResponseDTO<>(list);
    }

    @GetMapping("/list")
    @Operation(summary = "系统部门", description = "系统部门接口")
    public ResponseDTO<List<SysDepart>> list(DepartForm form) {
        log.info("收到请求->获取系统部门:[{}]", form);
        List<SysDepart> list = dictService.list(form);
        log.debug("返回结果->获取系统部门完成:[{}]", list);
        return new ResponseDTO<>(list);
    }

    @PostMapping("/save")
    @Operation(summary = "系统部门", description = "保存系统部门接口")
    public ResponseDTO<Boolean> saveDepart(@RequestBody DepartForm form) {
        log.info("收到请求->保存系统部门:[{}]", form);
        boolean status = dictService.saveDepart(form);
        log.debug("返回结果->保存系统部门完成:[{}]", status);
        return new ResponseDTO<>(status);
    }

    @DeleteMapping("/remove/{id}")
    @Operation(summary = "系统部门", description = "删除系统部门接口")
    public ResponseDTO<Boolean> deleteById(@PathVariable String id) {
        log.info("收到请求->删除系统部门:[{}]", id);
        boolean status = dictService.deleteById(id);
        log.debug("返回结果->删除系统部门完成:[{}]", status);
        return new ResponseDTO<>(status);
    }


}
