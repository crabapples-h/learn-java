package cn.crabapples.system.entity;

import cn.crabapples.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
 */
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
public class SysUser extends BaseEntity {

    @Column(columnDefinition = "varchar(32)  not null comment '用户名'", unique = true)
    private String username;

    @Column(columnDefinition = "varchar(64) not null comment '密码'")
    private String password;

    @Column(columnDefinition = "varchar(32)  not null comment '姓名'")
    private String name;

    @Column(columnDefinition = "varchar(32) comment '邮箱'")
    private String mail;

    @Column(columnDefinition = "varchar(15) comment '电话'")
    private String phone;

    @Column(columnDefinition = "tinyint default 0 not null comment '年龄'")
    private Integer age;

    @LastModifiedBy
    @Column(columnDefinition = "varchar(32) comment '最后操作用户'")
    private String lastModifiedBy;

//    @ElementCollection
    @JoinColumn
    @ManyToMany
    private List<SysRole> roleList;

    @Column(columnDefinition = "tinyint default 0 not null comment '用户状态标记 0:正常 1:禁用'")
    private Integer status;
}
