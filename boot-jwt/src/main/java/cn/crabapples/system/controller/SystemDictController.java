package cn.crabapples.system.controller;

import cn.crabapples.common.ResponseDTO;
import cn.crabapples.common.base.BaseController;
import cn.crabapples.system.entity.SysDict;
import cn.crabapples.system.form.DictForm;
import cn.crabapples.system.service.SystemDictService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("系统接口[字典]")
@Slf4j
@RequestMapping("/api/system/dict")
public class SystemDictController extends BaseController {

    private final SystemDictService dictService;

    public SystemDictController(SystemDictService dictService) {
        this.dictService = dictService;
    }


    @GetMapping("/page")
    @ApiOperation(value = "系统字典", notes = "系统字典接口")
    public ResponseDTO getDictPage(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                   @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                   DictForm form) {
        log.info("收到请求->获取系统字典:[{}]", form);
        IPage<SysDict> list = dictService.getDictPage(pageIndex, pageSize, form);
        log.debug("返回结果->获取系统字典:[{}]", list);
        return ResponseDTO.returnSuccess("登录成功", list);
    }
}
