package cn.crabapples.test.controller;

import cn.crabapples.common.Groups;
import cn.crabapples.common.ResponseDTO;
import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.jwt.JwtIgnore;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.test.form.DemoPostForm1;
import cn.crabapples.test.form.DemoPostForm2;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
    private final RestTemplate restTemplate;

    public ControllerWechatTest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static void main(String[] args) {
        getTicket();
    }

    public static String getToken() {
        String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx0f6e9947ab89a6e3&secret=34893470392a81e9a5ba9ad09037943f";
        JSONObject jsonObject = new RestTemplate().getForObject(getTokenUrl, JSONObject.class);
        return jsonObject.getString("access_token");
    }

    public static ResponseDTO getTicket() {
        String token = getToken();
        String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi";
        JSONObject jsonObject = new RestTemplate().getForObject(getTokenUrl, JSONObject.class);
        System.out.println(jsonObject.getString("ticket"));
        return ResponseDTO.returnSuccess(jsonObject);
    }

    public static String sign() {
        String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx0f6e9947ab89a6e3&secret=34893470392a81e9a5ba9ad09037943f";
        JSONObject jsonObject = new RestTemplate().getForObject(getTokenUrl, JSONObject.class);
        return jsonObject.getString("access_token");
    }
}
