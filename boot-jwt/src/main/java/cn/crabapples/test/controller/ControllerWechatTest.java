package cn.crabapples.test.controller;

import cn.crabapples.common.ResponseDTO;
import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.jwt.JwtIgnore;
import cn.crabapples.test.service.WechatTestService;
import cn.crabapples.test.vo.WechatConfig;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO 一个演示的controller
 *
 * @author Mr.He
 * 2019/8/5 22:51
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Api("测试接口")
@RestController
@RequestMapping(value = "/api/wechat")
@Slf4j
public class ControllerWechatTest extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ControllerWechatTest.class);
    private final WechatTestService service;

    public ControllerWechatTest(WechatTestService service) {
        this.service = service;
    }


    @GetMapping("/get/config")
    @JwtIgnore
    public ResponseDTO<WechatConfig> sign(@RequestParam String url) {
        log.info("获取微信sdk配置,url:[{}]", url);
        WechatConfig config = service.sign(url);
        return new ResponseDTO<>(config);
    }
}
