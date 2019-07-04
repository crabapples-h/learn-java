package Mr.He.spring.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author wishforyou.xia@gmail.com
 * @date 2019/7/4 14:51
 *
 * Entity 这是一个和数据库表相关联的类
 */
@Entity
public class DemoUser{
    public DemoUser() {
    }

    public DemoUser(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
    /**
     * Id 主键
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}