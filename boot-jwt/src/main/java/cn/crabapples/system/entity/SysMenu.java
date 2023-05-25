package cn.crabapples.system.entity;

import cn.crabapples.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * TODO 系统菜单
 *
 * @author Mr.He
 * 3/1/20 11:58 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Setter
@Getter
@Entity
@DynamicUpdate
@DynamicInsert
public class SysMenu extends BaseEntity {
    @Column(columnDefinition = "tinyint default -1 comment '排序'")
    private Integer sort;

    @Column(columnDefinition = "varchar(64) comment '菜单图标'")
    private String icon;

    @Column(columnDefinition = "varchar(64) comment '菜单名'")
    private String name;

    @Column(columnDefinition = "tinyint default 0 comment '菜单类型 1:目录 2:菜单 3:超链接 4:按钮'")
    private Integer menusType;

    @Column(columnDefinition = "varchar(64) default null comment '浏览器访问路径'")
    private String url;

    @Column(columnDefinition = "varchar(64) default null comment '文件存放路径'")
    private String componentPath;

    @Column(columnDefinition = "varchar(64) default null comment '授权标识'")
    private String permission;

    @ManyToOne
    @JoinColumn
    private SysMenu parent;

    @Column(columnDefinition = "tinyint default 0 comment '是否隐藏'")
    private Boolean hidden;

}
