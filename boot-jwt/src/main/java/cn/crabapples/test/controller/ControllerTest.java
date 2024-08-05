//package cn.crabapples.test.controller;
//
//import cn.crabapples.common.base.BaseController;
//import cn.crabapples.common.config.ApplicationConfigure;
//import cn.crabapples.common.ResponseDTO;
//import cn.crabapples.common.Groups;
//import cn.crabapples.common.jwt.JwtIgnore;
//import cn.crabapples.system.sysUser.entity.SysUser;
//import cn.crabapples.test.form.DemoPostForm1;
//import cn.crabapples.test.form.DemoPostForm2;
//import cn.crabapples.test.service.ServiceTest;
//import cn.crabapples.test.service.UserServiceTest;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//import java.util.List;
//
///**
// * TODO 一个演示的controller
// *
// * @author Mr.He
// * 2019/8/5 22:51
// * e-mail crabapples.cn@gmail.com
// * qq 294046317
// * pc-name 29404
// */
//@Api("测试接口")
//@RestController
//@RequestMapping(value = "/api/test")
//@Slf4j
//public class ControllerTest extends BaseController {
//
//    private static final Logger logger = LoggerFactory.getLogger(ControllerTest.class);
//    private final UserServiceTest userServiceTest;
//    private final RabbitTemplate rabbitTemplate;
//    private final ServiceTest serviceTest;
//    private final ApplicationConfigure applicationConfigure;
//
//    public ControllerTest(UserServiceTest userServiceTest,
//                          RabbitTemplate rabbitTemplate,
//                          ServiceTest serviceTest, ApplicationConfigure applicationConfigure) {
//        this.userServiceTest = userServiceTest;
//        this.rabbitTemplate = rabbitTemplate;
//        this.serviceTest = serviceTest;
//        this.applicationConfigure = applicationConfigure;
//    }
//
//    @GetMapping("/mac/test")
//    @JwtIgnore
//    public ResponseDTO macTest(HttpServletRequest request) {
//        serviceTest.getIpAddress(request);
//        System.err.println(applicationConfigure);
//        List<SysUser> userList = userServiceTest.findByHQL("alice");
//        return ResponseDTO.returnSuccess("切换数据源1", userList);
//    }
//    @GetMapping("/datasource/test1")
//    @JwtIgnore
//    public ResponseDTO dataSourceTest1() {
//        System.err.println(applicationConfigure);
//        List<SysUser> userList = userServiceTest.findByHQL("alice");
//        return ResponseDTO.returnSuccess("切换数据源1", userList);
//    }
//
//    @GetMapping("/datasource/test2")
//    @JwtIgnore
//    public ResponseDTO dataSourceTest2() {
//        List<SysUser> userList = userServiceTest.findByHQL("admin");
//        return ResponseDTO.returnSuccess("切换数据源2", userList);
//    }
//    String routingKey = "noticeKey";
//    String queueName = "notice";
//    String exchangeName = "noticeExchange";
//
//    @GetMapping("/mq/send")
//    public ResponseDTO mqSend() {
//        rabbitTemplate.convertAndSend(exchangeName, routingKey, "hello world");
//        /*
//         correlationData：用于获取相关信息。convertAndSend的某个重载方法中设置了此信息。
//         b：交换机是否成功收到生产者发的信息。
//         s：若b为false，即交换机接收失败，s中有导致失败的原因。
//         */
//        rabbitTemplate.setConfirmCallback((correlationData, b, s) -> {
//            log.info("交换机接收数据状态:[{}]", b);
//            if (!b) {
//                log.info("交换机接收数据出现异常:[{}]", s);
//            }
//        });
//        return ResponseDTO.returnSuccess("消息发送成功");
//    }
//
//    @GetMapping("/mq/get")
//    public ResponseDTO mqGet() {
////        final RabbitTemplate rabbitTemplate = rabbitAdmin.getRabbitTemplate();
//        Message message = rabbitTemplate.receive(queueName);
//        System.err.println(message);
//        return ResponseDTO.returnSuccess("消息消费成功", message);
//    }
//
//
//    @GetMapping("/hello")
//    @ApiOperation(value = "hello,world测试", notes = "这个是一个测试接口")
//    @ApiImplicitParam(name = "name", value = "这个是参数", required = true, dataType = "String", paramType = "query")
//    public String helloDemo(@RequestParam String name) {
//        return name + " say: hello world";
//    }
//
//    /**
//     * TODO 测试从RequestBody中获取数据并验证
//     *
//     * @RequestBody 表示从请求体中获取数据
//     * @Valid 表示需要验证对应属性
//     * <p>
//     * 参数中传入BindingResult对象时，无论参数校验是否通过都会执行方法
//     * 可通过bindingResult.hasErrors()判断参数校验是否通过
//     */
//    @PostMapping("/postDemo1")
//    @ApiOperation(value = "属性校验测试", notes = "属性校验测试接口")
//    public ResponseDTO postDemo1(@Valid @RequestBody DemoPostForm1 demoPostForm, BindingResult bindingResult) {
//        logger.info("提交的参数:{}", demoPostForm);
//        if (bindingResult.hasErrors()) {
//            String errorMessage = bindingResult.getFieldErrors().get(0).getDefaultMessage();
//            logger.error("验证信息:{}", errorMessage);
//            return ResponseDTO.returnError(errorMessage);
//        }
//        return ResponseDTO.returnSuccess("成功", demoPostForm);
//    }
//
//    /**
//     * TODO 测试使用不同的校验组校验参数，根据参数中的type判断使用哪个校验组
//     */
//    @PostMapping("/postDemo2")
//    public ResponseDTO postDemo2(@RequestBody DemoPostForm2 demoPostForm) {
//        try {
//            if (demoPostForm.getType() == 0) {
//                super.validator(demoPostForm, Groups.IsNull.class);
//            } else {
//                super.validator(demoPostForm, Groups.IsNotNull.class);
//            }
//            return ResponseDTO.returnSuccess("收到");
//        } catch (Exception e) {
//            assert false : "";
//            return ResponseDTO.returnError(e.getMessage());
//        }
//    }
//
//}
