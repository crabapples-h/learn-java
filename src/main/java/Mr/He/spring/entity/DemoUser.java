package Mr.He.spring.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO
 *
 * @author Mr.He
 * @date 2019/7/4 14:51
 * e-mail wishforyou.xia@gmail.com
 * qq 294046317
 * pc-name 29404
 *
 * Entity 表示这是一个和数据库表相关联的类
 * Table name = "demo_user" 设置表名为 demo_user
 */
@Entity
@Table(name = "demo_user")
@Getter
@Setter
public class DemoUser{
    public DemoUser() {
    }

    public DemoUser(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
    /**
     * Id uuid主键
     * GeneratedValue 自增长
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    /**
     * Column length=30 数据字段长度为30
     */
    @Column(length = 30)
    private String name;

    /**
     * Column nullable = false 数据字段不能为空
     */
    @Column(nullable = false)
    private Integer age;

    /**
     * 创建时间
     * columnDefinition 设置默认值为当前时间
     */
    @Column(columnDefinition = "timestamp default current_timestamp comment '创建时间'")
    private LocalDateTime createTime;

    /**
     * 更新时间
     * columnDefinition 设置默认值为当前时间，随每次更新数据时更新时间
     */
    @Column(columnDefinition = "timestamp default current_timestamp on update current_timestamp comment '修改时间'")
    private LocalDateTime updateTime;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}