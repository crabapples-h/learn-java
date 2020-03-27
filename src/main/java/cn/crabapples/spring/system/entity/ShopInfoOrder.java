package cn.crabapples.spring.system.entity;

import cn.crabapples.spring.system.common.BaseEntity;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * TODO
 *
 * @author Mr.He
 * 2020/3/18 23:45
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Entity
@Getter
@Setter
public class ShopInfoOrder extends BaseEntity {
    private String listId;
    private String title;
    private String img;
    private String name;
    private String content;
    private Integer count;
    private BigDecimal amt;
    private BigDecimal sum;
    @Column(columnDefinition="int(1) default 0 comment '状态' ")
    private Integer status = 0;
    @Column(columnDefinition="varchar(10) comment '序号' ")
    private String sort;
    @Column(columnDefinition="varchar(64) comment '订单号' ")
    private String orderNumber;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
