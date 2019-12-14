package Mr.He.spring.entity;

import Mr.He.spring.common.BaseEntity;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO 用户实体类
 *
 * @author Mr.He
 * @date 2019/7/4 14:51
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
    private String name;

    /**
     * Column nullable = false 数据字段不能为空
     */
    @Column(columnDefinition = "int (2) default 0 not null")
    private Integer age;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}