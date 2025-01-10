package cn.crabapples.common.base;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.core.activerecord.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

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
@Accessors(chain = true)
public abstract class BaseEntity<T extends Model<T>> extends Model<T> implements Serializable, Cloneable {

    // 创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
//    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    // 更新时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
//    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    //创建人
    private String createBy;

    //更新人
    private String updateBy;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public T toDTO(T dto) {
        BeanUtils.copyProperties(this, dto);
        return dto;
    }
}
