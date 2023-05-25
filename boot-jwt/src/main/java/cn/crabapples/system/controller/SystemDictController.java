package cn.crabapples.system.controller;

import cn.crabapples.common.Groups;
import cn.crabapples.common.ResponseDTO;
import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.jwt.JwtIgnore;
import cn.crabapples.system.entity.SysDict;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.DictForm;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.SystemDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TODO 系统相关接口
 *
 * @author Mr.He
 * 2020/1/28 23:17
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */

@RestController
@Api("系统管理")
@Slf4j
@RequestMapping("/api/system/dict")
public class SystemDictController extends BaseController {

    private final SystemDictService dictService;

    public SystemDictController(SystemDictService dictService) {
        this.dictService = dictService;
    }

    @JwtIgnore
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取字典列表", notes = "获取字典列表接口")
    public ResponseDTO queryList(@RequestBody(required = false) DictForm form) {
        Iterable<SysDict> list = dictService.queryList(form);
        return ResponseDTO.returnSuccess("操作成功", list);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存字典", notes = "保存字典接口")
    public ResponseDTO save(DictForm form) {
        dictService.save(form);
        return ResponseDTO.returnSuccess("操作成功");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除字典", notes = "删除字典接口")
    public ResponseDTO deleteById(@PathVariable String id) {
        dictService.deleteById(id);
        return ResponseDTO.returnSuccess("操作成功");
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "获取字典详情", notes = "获取字典接口")
    public ResponseDTO getById(@PathVariable String id) {
        SysDict entity = dictService.getById(id);
        return ResponseDTO.returnSuccess("操作成功",entity);
    }
}
