package cn.crabapples.test.controller;

import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.base.ResponseDTO;
import cn.crabapples.common.annotation.JwtIgnore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value = "/api/banner")
@Slf4j
public class FlexController extends BaseController {
    @GetMapping("/get/list")
    @JwtIgnore
    public Mono<ResponseDTO<Object>> getList() {
        return Mono.create((monoSink) -> monoSink.success(new ResponseDTO<>()));
    }
}
