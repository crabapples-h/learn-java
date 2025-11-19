package cn.crabapples.system.system.controller;

import cn.crabapples.common.annotation.IgnoreDict;
import cn.crabapples.common.base.BaseController;
import cn.crabapples.common.base.ResponseDTO;
import cn.crabapples.common.annotation.JwtIgnore;
import cn.crabapples.common.utils.Groups;
import cn.crabapples.system.dto.SysOptionDTO;
import cn.crabapples.system.sysUser.entity.SysUser;
import cn.crabapples.system.sysUser.form.UserForm;
import cn.crabapples.system.sysUser.service.SystemUserService;
import cn.crabapples.system.system.service.SystemService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
@RequestMapping("/api/system/")
@Slf4j
@Tag(name = "系统功能")
public class SystemController extends BaseController {

    private final SystemUserService userService;
    private final SystemService sysService;
    private final ApplicationContext applicationContext;

    @Value("${previewAddress}")
    private String previewAddress;

    public SystemController(SystemUserService userService, SystemService sysService, ApplicationContext applicationContext) {
        this.userService = userService;
        this.sysService = sysService;
        this.applicationContext = applicationContext;
    }

//    /**
//     * 发起登录请求-流控接管
//     *
//     * @param form 用户名和密码
//     * @return 登录成功返回token
//     */
//    @JwtIgnore
//    @IgnoreDict
//    public ResponseDTO<String> loginCallback(@RequestBody UserForm form) {
//        log.warn("收到请求->用户登陆验证:[{}],------流控接管-----", form);
//        return new ResponseDTO<String>().returnCustom(429, "流控接管", null);
//    }

    /**
     * 发起登录请求
     *
     * @param form 用户名和密码
     * @return 登录成功返回token
     */
    @JwtIgnore
    @PostMapping(value = "/login")
    @Operation(summary = "用户登陆", description = "用户登陆接口")
//    @SentinelResource(value = "login", blockHandler = "loginCallback")
    @IgnoreDict
    public ResponseDTO<String> login(@RequestBody UserForm form) {
        log.info("收到请求->用户登陆验证:[{}]", form);
        super.validator(form, Groups.IsLogin.class);
        String token = sysService.login(form);
        log.info("返回结果->登录成功->token:[{}]", token);
        return new ResponseDTO<>("登录成功", token);
    }

    /**
     * 注销登录
     */
    @JwtIgnore
    @PostMapping("/logout")
    @Operation(summary = "注销登录", description = "注销登录接口")
    public ResponseDTO<String> logout() {
        return new ResponseDTO<>("注销成功");
    }

    @GetMapping("/permissions")
    public ResponseDTO<List<String>> getUserPermissions() {
        log.info("收到请求->获取用户权限列表");
        List<String> list = sysService.getUserPermissions();
        log.debug("返回结果->获取用户权限列表成功:[{}]", list);
        return new ResponseDTO<>(list);
    }

    @GetMapping("/userInfo")
    @Operation(summary = "获取当前用户信息", description = "获取当前用户信息接口")
    public ResponseDTO<SysUser> getUserInfo() {
        log.info("收到请求->获取当前用户信息");
        SysUser entity = userService.getUserInfo();
        log.info("返回结果->获取当前用户信息结束:[{}]", entity);
        return new ResponseDTO<>(entity);
    }

    @GetMapping("/checkUsername/{username}")
    @Operation(summary = "检测用户名是否被使用", description = "检测用户名是否被使用接口")
    public ResponseDTO<Boolean> checkUsername(@PathVariable String username) {
        log.info("收到请求->检测用户名是否被使用:[{}]", username);
        boolean exist = sysService.checkUsername(username);
        log.info("返回结果->检测用户名是否被使用:[{}]", exist);
        return new ResponseDTO<>("用户名未被使用", exist);
    }

    @GetMapping("/dict/options/{code}")
    @Operation(summary = "根据字典渲染下拉项", description = "根据字典渲染下拉项")
    public ResponseDTO<List<SysOptionDTO>> dictSelectOptions(@PathVariable String code) {
        log.info("收到请求->根据字典渲染下拉项:[{}]", code);
        List<SysOptionDTO> options = sysService.dictSelectOptions(code);
        log.debug("返回结果->根据字典渲染下拉项:[{}]", options);
        return new ResponseDTO<>("操作成功", options);
    }

    @GetMapping("/server/address")
    @Operation(summary = "服务器地址", description = "获取服务器地址")
    public ResponseDTO<String> serverAddress() throws UnknownHostException {
        Environment env = applicationContext.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        log.info("收到请求->获取服务器地址");
        String url = "http://" + ip + ":" + port;
        log.info("返回结果->获取服务器地址结束:[{}]", url);
        return new ResponseDTO<>(url);
    }

    @GetMapping("/preview/address")
    @Operation(summary = "文件预览服务器地址", description = "获取文件预览服务器地址")
    public ResponseDTO<String> previewAddress() {
        log.info("收到请求->文件预览服务器地址");
        log.info("返回结果->文件预览服务器地址:[{}]", previewAddress);
        return new ResponseDTO<>(previewAddress);
    }


}
