package cn.crabapples.spring.crabapples.service.impl;

import cn.crabapples.spring.crabapples.dao.OrderInfoRepository;
import cn.crabapples.spring.crabapples.dao.ShopInfoOrderRepository;
import cn.crabapples.spring.crabapples.dao.ShopInfoRepository;
import cn.crabapples.spring.crabapples.dao.ShopListRepository;
import cn.crabapples.spring.crabapples.entity.OrderInfo;
import cn.crabapples.spring.crabapples.entity.ShopInfo;
import cn.crabapples.spring.crabapples.entity.ShopInfoOrder;
import cn.crabapples.spring.crabapples.entity.ShopList;
import cn.crabapples.spring.crabapples.service.ShopService;
import cn.crabapples.system.common.ApplicationException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Mr.He
 * 2020/3/18 23:46
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Service
public class ShopServiceImpl implements ShopService {
    private static Integer SORT = 1;
    private final ShopListRepository menuListRepository;
    private final ShopInfoRepository menuInfoRepository;
    private final ShopInfoOrderRepository submitMenuInfoRepository;
    private final OrderInfoRepository orderInfoRepository;


    public ShopServiceImpl(ShopListRepository menuListRepository, ShopInfoRepository menuInfoRepository, ShopInfoOrderRepository submitMenuInfoRepository, OrderInfoRepository orderInfoRepository) {
        this.menuListRepository = menuListRepository;
        this.menuInfoRepository = menuInfoRepository;
        this.submitMenuInfoRepository = submitMenuInfoRepository;
        this.orderInfoRepository = orderInfoRepository;
    }


    @Override
    public List<ShopList> findAllShopList() {
        return menuListRepository.findAll();
    }

    @Override
    public List<ShopInfo> findShopInfoByListId(String id) {
        return menuInfoRepository.findByListId(id);
    }

    @Override
    public void deleteGoods(List<String> ids) {
//        ids.forEach(menuInfoRepository::deleteById);
    }

    @Override
    public ShopInfo changeStatus(String id) {
        ShopInfo shopInfo = menuInfoRepository.findById(id).orElse(null);
        if (null != shopInfo) {
            shopInfo.setStatus(!shopInfo.isStatus());
            return menuInfoRepository.save(shopInfo);
        }
        throw new ApplicationException("商品信息异常");
    }

    @Override
    public List<ShopInfo> findAllShopInfo() {
        return menuInfoRepository.findAll();
    }

    @Override
    public synchronized OrderInfo saveShopInfoOrders(List<ShopInfo> shopInfos) {
        StringBuilder sort = new StringBuilder(SORT.toString());
        BigDecimal sum = new BigDecimal("0");
        while (sort.length() < 5) {
            sort.insert(0, "0");
        }
        sort.insert(0, "A");
        String orderNumber = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + sort;

        List<ShopInfoOrder> orders = new ArrayList<>();
        for (ShopInfo shopInfo : shopInfos) {
            ShopInfoOrder submit = new ShopInfoOrder();
            sum = sum.add(shopInfo.getSum());
            submit.setSort(sort.toString());
            submit.setOrderNumber(orderNumber);
            BeanUtils.copyProperties(shopInfo, submit);
            orders.add(submit);
        }
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNumber(orderNumber);
        orderInfo.setSum(sum);
        orderInfo.setSort(sort.toString());
        orderInfo.setTip("喜欢你~");
        orderInfo.setStatusText("正在精心制作中");
        SORT++;
        submitMenuInfoRepository.saveAll(orders);
        orderInfo = orderInfoRepository.save(orderInfo);
        return orderInfo;
    }

}
