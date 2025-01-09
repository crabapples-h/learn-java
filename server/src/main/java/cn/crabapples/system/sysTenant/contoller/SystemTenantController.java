package cn.crabapples.system.sysTenant.contoller;

import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.base.ResponseDTO;
import cn.crabapples.system.sysTenant.entity.SysTenant;
import cn.crabapples.system.sysTenant.form.TenantForm;
import cn.crabapples.system.sysTenant.service.SystemTenantService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "系统接口[租户]")
@Slf4j
@RequestMapping("/api/system/tenant")
public class SystemTenantController extends BaseController {

    private final SystemTenantService dictService;

    public SystemTenantController(SystemTenantService dictService) {
        this.dictService = dictService;
    }


    @GetMapping("/page")
    @Operation(summary = "系统租户", description = "系统租户接口")
    public ResponseDTO<Page<SysTenant>> page(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                             @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                             TenantForm form) {
        log.info("收到请求->获取[分页]系统租户:[{}]", form);
        Page<SysTenant> list = dictService.page(pageIndex, pageSize, form);
        log.debug("返回结果->获取[分页]系统租户完成:[{}]", list);
        return new ResponseDTO<>(list);
    }

    @GetMapping("/list")
    @Operation(summary = "系统租户", description = "系统租户接口")
    public ResponseDTO<List<SysTenant>> list(TenantForm form) {
        log.info("收到请求->获取系统租户:[{}]", form);
        List<SysTenant> list = dictService.list(form);
        log.debug("返回结果->获取系统租户完成:[{}]", list);
        return new ResponseDTO<>(list);
    }

    @PostMapping("/save")
    @Operation(summary = "系统租户", description = "保存系统租户接口")
    public ResponseDTO<Boolean> saveTenant(@RequestBody TenantForm form) {
        log.info("收到请求->保存系统租户:[{}]", form);
        boolean status = dictService.saveTenant(form);
        log.debug("返回结果->保存系统租户完成:[{}]", status);
        return new ResponseDTO<>(status);
    }

    @DeleteMapping("/remove/{id}")
    @Operation(summary = "系统租户", description = "删除系统租户接口")
    public ResponseDTO<Boolean> deleteById(@PathVariable String id) {
        log.info("收到请求->删除系统租户:[{}]", id);
        boolean status = dictService.deleteById(id);
        log.debug("返回结果->删除系统租户完成:[{}]", status);
        return new ResponseDTO<>(status);
    }


}
