package cn.crabapples.system.entity;

import cn.crabapples.common.base.BaseEntity;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.util.List;

/**
 * TODO 用户实体类
 *
 * @author Mr.He
 * 2019/7/4 14:51
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 * <p>
 * Entity 表示这是一个和数据库表相关联的类
 */
@Entity
@Getter
@Setter
public class SysUser extends BaseEntity {

    @Column(columnDefinition = "varchar(32) comment '用户名'", unique = true)
    private String username;

    @Column(columnDefinition = "varchar(64) comment '密码'")
    private String password;

    @Column(columnDefinition = "varchar(32) comment '姓名'")
    private String name;

    @Column(columnDefinition = "varchar(15) comment '电话'")
    private String phone;

    /**
     * Column nullable = false 数据字段不能为空
     */
    @Column(columnDefinition = "tinyint default 0 not null comment '年龄'")
    private Integer age;

    @LastModifiedBy
    @Column(columnDefinition = "varchar(32) comment '最后操作用户'")
    private String lastModifiedBy;

    @Transient
//    @ManyToMany(fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private List<SysRoles> roles;

    @Column(columnDefinition = "longtext comment '角色列表'")
    private String rolesList = "";

    @Column(columnDefinition = "tinyint default 0 not null comment '用户状态标记 0:正常 1:禁用'")
    private int status;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
