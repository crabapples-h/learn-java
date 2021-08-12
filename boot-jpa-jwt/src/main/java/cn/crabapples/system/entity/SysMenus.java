package cn.crabapples.system.entity;

import cn.crabapples.common.base.BaseEntity;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

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
public class SysMenus extends BaseEntity {
    @Column(columnDefinition = "tinyint(4) default -1 comment '排序'")
    private Integer sort;

    @Column(columnDefinition = "varchar(64) comment '菜单图标'")
    private String icon;

    @Column(columnDefinition = "varchar(64) comment '菜单名'")
    private String name;

    @Column(columnDefinition = "tinyint(2) default 0 comment '菜单类型 1:目录 2:菜单 3:外链 4:按钮'")
    private Integer menusType;

    @Column(columnDefinition = "tinyint(1) default 0 comment '是否为跟目录 0:是 1:否'")
    private int isRoot;

    @Column(columnDefinition = "varchar(64) default null comment '浏览器访问路径'")
    private String path;

    @Column(columnDefinition = "varchar(64) default null comment '文件存放路径'")
    private String filePath;

    @Column(columnDefinition = "varchar(64) default null comment '授权标识'")
    private String permission;

    @OneToMany
    private List<SysMenus> children;

    @Transient
    @JSONField(serialize = false)
    private boolean showFlag;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
