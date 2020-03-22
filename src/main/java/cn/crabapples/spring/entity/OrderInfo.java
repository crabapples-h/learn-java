package cn.crabapples.spring.entity;

import cn.crabapples.spring.common.BaseEntity;
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
public class OrderInfo extends BaseEntity {
    @Column(columnDefinition="varchar(10) comment '序号' ")
    private String sort;
    @Column(columnDefinition = "int(1) default 0 comment '订单状态'")
    private Integer status;
    private String statusText;
    private String tip;
    @Column(columnDefinition = "decimal(4) comment '订单金额'")
    private BigDecimal sum;
    private String orderNumber;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
