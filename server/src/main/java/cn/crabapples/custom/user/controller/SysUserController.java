package cn.crabapples.custom.user.controller;

import lombok.extern.slf4j.Slf4j;
import cn.crabapples.common.base.ResponseDTO;
import cn.crabapples.common.jwt.JwtIgnore;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import cn.crabapples.custom.user.service.SysUserService;
import cn.crabapples.custom.user.entity.SysUser;


import java.util.List;

@RestController
@RequestMapping(value = "/api/Test")
@Slf4j
public class SysUserController {
    private final SysUserService service;

    public SysUserController(SysUserService service) {
        this.service = service;
    }

    @RequestMapping("/list")
    public ResponseDTO list() {
        List<SysUser> list = service.queryList();
        return new ResponseDTO<>(list);
    }

    @PostMapping("/save")
    public ResponseDTO<Boolean> save(@RequestBody SysUser entity) {
        log.info("收到请求->保存SysUser");
        boolean status = entity.insertOrUpdate();
        log.info("返回结果->保存SysUser完成:[{}]", status);
        return new ResponseDTO<>(status);
    }

    @GetMapping("/page")
    public ResponseDTO<Page<SysUser>> page(SysUser entity,
                                        @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("收到请求->获取SysUser分页");
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>(entity).select();
        wrapper.orderByDesc("create_time");
        Page<SysUser> page = new Page<>(pageIndex, pageSize);
        Page<SysUser> data = service.page(page, wrapper);
        log.info("返回结果->获取SysUser分页完成:[{}]", data);
        return new ResponseDTO<>(data);
    }

    @GetMapping("/list")
    @JwtIgnore
    public ResponseDTO<List<SysUser>> list(SysUser entity) {
        log.info("收到请求->获取SysUser列表");
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>(entity).select();
        wrapper.orderByDesc("create_time");
        List<SysUser> data = service.list(wrapper);
        log.info("返回结果->获取SysUser列表完成:[{}]", data);
        return new ResponseDTO<>(data);
    }


    @GetMapping("/get/{id}")
    public ResponseDTO<SysUser> info(@PathVariable String id) {
        log.info("收到请求->获取SysUser详情");
        SysUser data = SysUser.create().setId(id).selectById();
        log.info("返回结果->获取SysUser详情完成:[{}]", data);
        return new ResponseDTO<>(data);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO<Boolean> remove(@PathVariable String id) {
        log.info("收到请求->删除SysUser");
        boolean status = SysUser.create().setId(id).deleteById();
        log.info("返回结果->删除SysUser完成:[{}]", status);
        return new ResponseDTO<>(status);
    }
}
