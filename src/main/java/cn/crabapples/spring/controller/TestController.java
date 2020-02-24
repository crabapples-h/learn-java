package cn.crabapples.spring.controller;

import cn.crabapples.spring.common.BaseController;
import cn.crabapples.spring.common.groups.IsLogin;
import cn.crabapples.spring.dto.ResponseDTO;
import cn.crabapples.spring.form.UserForm;
import cn.crabapples.spring.service.SysService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @GetMapping("/welcome1")
    public String welcome1(){
        logger.info("收到请求->welcome1");
        return PREFIX + "welcome1";
    }

    @GetMapping("/memberList")
    public String memberList(){
        logger.info("收到请求->memberList");
        return PREFIX + "member-list";
    }

    @GetMapping("/memberList1")
    public String memberList1(){
        logger.info("收到请求->memberList1");
        return PREFIX + "member-list1";
    }

    @GetMapping("/memberDel")
    public String memberDel(){
        logger.info("收到请求->memberDel");
        return PREFIX + "member-del";
    }
}
