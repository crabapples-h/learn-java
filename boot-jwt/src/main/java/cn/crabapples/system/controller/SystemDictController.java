package cn.crabapples.system.controller;

import cn.crabapples.common.ResponseDTO;
import cn.crabapples.common.base.BaseController;
import cn.crabapples.system.entity.SysDict;
import cn.crabapples.system.entity.SysDictItem;
import cn.crabapples.system.form.DictForm;
import cn.crabapples.system.form.DictItemForm;
import cn.crabapples.system.service.SystemDictService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseDTO<IPage<SysDict>> getDictPage(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                   @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                   DictForm form) {
        log.info("收到请求->获取系统字典:[{}]", form);
        IPage<SysDict> list = dictService.getDictPage(pageIndex, pageSize, form);
        log.debug("返回结果->获取系统字典完成:[{}]", list);
        return new ResponseDTO<>(list);
    }

    @PostMapping("/save")
    @ApiOperation(value = "系统字典", notes = "保存系统字典接口")
    public ResponseDTO<Boolean> saveDict(@RequestBody DictForm form) {
        log.info("收到请求->保存系统字典:[{}]", form);
        boolean status = dictService.saveDict(form);
        log.debug("返回结果->保存系统字典完成:[{}]", status);
        return new ResponseDTO<>(status);
    }

    @PostMapping("/remove/{id}")
    @ApiOperation(value = "系统字典", notes = "删除系统字典接口")
    public ResponseDTO<Boolean> deleteById(@PathVariable String id) {
        log.info("收到请求->删除系统字典:[{}]", id);
        boolean status = dictService.deleteById(id);
        log.debug("返回结果->删除系统字典完成:[{}]", status);
        return new ResponseDTO<>(status);
    }

    @PostMapping("/item/save")
    @ApiOperation(value = "系统字典", notes = "保存系统字典项接口")
    public ResponseDTO<Boolean> saveDictItem(@RequestBody DictItemForm form) {
        log.info("收到请求->保存系统字典项:[{}]", form);
        boolean status = dictService.saveDictItem(form);
        log.debug("返回结果->保存系统字典项完成:[{}]", status);
        return new ResponseDTO<>(status);
    }

    @GetMapping("/item/list/code/{code}")
    @ApiOperation(value = "系统字典", notes = "获取系统字典项接口{code}")
    public ResponseDTO<List<SysDictItem>> getDictItemListByCode(@PathVariable String code) {
        log.info("收到请求->获取系统字典项,code:[{}]", code);
        List<SysDictItem> list = dictService.getDictItemListByCode(code);
        log.debug("返回结果->获取系统字典项完成:[{}]", list);
        return new ResponseDTO<>(list);
    }


    @GetMapping("/item/list/id/{id}")
    @ApiOperation(value = "系统字典", notes = "获取系统字典项接口{id}")
    public ResponseDTO<List<SysDictItem>> getDictItemListById(@PathVariable String id) {
        log.info("收到请求->获取系统字典项,id:[{}]", id);
        List<SysDictItem> list = dictService.getDictItemListById(id);
        log.debug("返回结果->获取系统字典项完成:[{}]", list);
        return new ResponseDTO<>(list);
    }

    @PostMapping("/item/remove/{id}")
    @ApiOperation(value = "系统字典", notes = "删除系统字典接口")
    public ResponseDTO<Boolean> deleteItemById(@PathVariable String id) {
        log.info("收到请求->删除系统字典项:[{}]", id);
        boolean status = dictService.deleteItemById(id);
        log.debug("返回结果->删除系统字典项完成:[{}]", status);
        return new ResponseDTO<>(status);
    }
}
