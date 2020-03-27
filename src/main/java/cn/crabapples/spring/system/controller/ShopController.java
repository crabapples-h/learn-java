package cn.crabapples.spring.system.controller;

import cn.crabapples.spring.system.common.BaseController;
import cn.crabapples.spring.system.dto.ResponseDTO;
import cn.crabapples.spring.system.entity.OrderInfo;
import cn.crabapples.spring.system.entity.ShopInfo;
import cn.crabapples.spring.system.entity.ShopList;
import cn.crabapples.spring.system.service.ShopService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * TODO
 *
 * @author Mr.He
 * 2020/3/18 23:36
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Controller
@ResponseBody
@RequestMapping(value = "/api/shop")
@Api("用户管理")
public class ShopController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ShopController.class);
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @RequestMapping("/getMenuList")
    public ResponseDTO getMenuList() {
        logger.info("收到请求->获取菜单列表");
        List<ShopList> shopLists = shopService.findAllShopList();
        logger.info("获取菜单列表->菜单列表信息:[{}]", shopLists);
        return ResponseDTO.returnSuccess("操作成功", shopLists);
    }

    @RequestMapping("/getGoodsInfoById/{id}")
    public ResponseDTO getGoodsInfoById(@PathVariable String id) {
        logger.info("收到请求->获取菜单详情:[{}]", id);
        List<ShopInfo> shopInfos = shopService.findShopInfoByListId(id);
        logger.info("获取菜单详情->菜单详情信息:[{}]", shopInfos);
        return ResponseDTO.returnSuccess("操作成功", shopInfos);
    }

    @RequestMapping("/getAllGoodsInfo")
    public ResponseDTO getAllGoodsInfo() {
        logger.info("收到请求->获取菜单详情");
        List<ShopInfo> shopInfos = shopService.findAllShopInfo();
        logger.info("获取菜单详情->菜单详情信息:[{}]", shopInfos);
        return ResponseDTO.returnSuccess("操作成功", shopInfos);
    }

    @PostMapping("/submit")
    public ResponseDTO submit(@RequestBody List<ShopInfo> shopInfos) {
        logger.info("收到请求->确认订单:[{}]", shopInfos);
        OrderInfo orderInfo = shopService.saveShopInfoOrders(shopInfos);
        logger.info("下单成功->订单信息:[{}]",orderInfo);
        return ResponseDTO.returnSuccess("操作成功",orderInfo);
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseDTO changeStatus(@PathVariable String id){
        logger.info("收到请求->修改商品状态:[{}]",id);
        shopService.changeStatus(id);
        logger.info("修改商品状态完成");
        return ResponseDTO.returnSuccess("操作成功");
    }

    @DeleteMapping("/remove")
    public ResponseDTO delete(@RequestBody List<String> ids){
        logger.info("收到请求->删除商品:[{}]",ids);
        shopService.deleteGoods(ids);
        logger.info("删除商品完成");
        return ResponseDTO.returnSuccess("操作成功");
    }
//
//    @PostMapping("/activeUser")
//    @ApiOperation(value = "激活用户", notes = "激活用户接口")
//    public ResponseDTO activeUser(@RequestBody UserForm form){
//        super.validator(form, IsStatusChange.class);
//        logger.info("收到请求->激活用户:[{}]",form.getId());
//        userService.activeUser(form.getId());
//        logger.info("用户激活完成");
//        return ResponseDTO.returnSuccess("操作成功");
//    }

}
