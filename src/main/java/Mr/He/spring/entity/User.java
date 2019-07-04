package Mr.He.spring.entity;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author wishforyou.xia@gmail.com
 * @date 2019/7/4 14:51
 *
 * Entity 这是一个和数据库表相关联的类
 */
@Entity
public class User{
    public User() {
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
    /**
     * Id 主键
     * GeneratedValue 自增长
     */
    @Id
    @GeneratedValue
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

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}