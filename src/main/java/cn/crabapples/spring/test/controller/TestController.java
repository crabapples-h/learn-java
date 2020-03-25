package cn.crabapples.spring.test.controller;

import cn.crabapples.spring.common.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO 系统相关接口
 *
 * @author Mr.He
 * 2020/1/28 23:17
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */

@Controller
public class TestController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);
    private final String PREFIX = "crabapples/";

    @GetMapping("/view/{pageName}")
    public String welcome1(@PathVariable("pageName") String pageName) {
        logger.info("收到请求->[{}]", pageName);
        return PREFIX + pageName;
    }

    @GetMapping("/memberList")
    public String memberList() {
        logger.info("收到请求->memberList");
        return PREFIX + "member-list";
    }

    @GetMapping("/memberList1")
    public String memberList1() {
        logger.info("收到请求->memberList1");
        return PREFIX + "member-list1";
    }

    @GetMapping("/memberDel")
    public String memberDel() {
        logger.info("收到请求->memberDel");
        return PREFIX + "member-del";
    }
}
