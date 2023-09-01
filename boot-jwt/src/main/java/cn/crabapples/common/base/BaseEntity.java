package cn.crabapples.common.base;

import cn.crabapples.common.Dict;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.mybatisflex.annotation.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * TODO 实体类基础属性
 *
 * @author Mr.He
 * 2019/9/21 17:47
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Getter
@Setter
public abstract class BaseEntity implements Serializable, Cloneable {
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public <T> T toDTO(T dto) {
        BeanUtils.copyProperties(this, dto);
        return dto;
    }
}
