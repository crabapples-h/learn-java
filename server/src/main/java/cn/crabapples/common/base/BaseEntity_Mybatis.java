
package cn.crabapples.common.base;

import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 实体类基础属性
 */
@Getter
@Setter
public abstract class BaseEntity_Mybatis implements Serializable, Cloneable {
    private String id;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
