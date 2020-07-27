package cn.crabapples.spring.crabapples.entity;

import cn.crabapples.system.common.BaseEntity;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

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
public class ShopList extends BaseEntity {
    private String name;
    @OneToMany
    private List<ShopInfo> shopInfos;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
