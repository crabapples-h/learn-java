package Mr.He.spring.entity;

import Mr.He.spring.common.BaseEntity;
import Mr.He.spring.groups.IsNotNull;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * TODO 用户实体类
 *
 * @author Mr.He
  * 2019/7/4 14:51
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 *
 * Entity 表示这是一个和数据库表相关联的类
 * Table name = "user" 设置表名为 user
 */
@Entity
@Table(name = "user")
@Getter
@Setter
public class User extends BaseEntity {
    public User() {}
    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Column length=30 数据字段长度为30
     */
    @Column(length = 30)
    @NotBlank(message = "姓名不能为空", groups = IsNotNull.class)
    private String name;

    /**
     * Column nullable = false 数据字段不能为空
     */
    @Column(columnDefinition = "int (3) default 0 not null")
    @NotNull(message = "年龄不能为空", groups = IsNotNull.class)
    private Integer age;

    /**
     * 状态标记 0:正常 1:禁用
     */
    @Column(columnDefinition = "bit(1) default 0 not null comment '状态标记'")
    @NotBlank(message = "状态不能为空", groups = IsNotNull.class)
    private int status;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}