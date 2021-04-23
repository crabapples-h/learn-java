package cn.crabapples.system.entity;

import cn.crabapples.common.base.BaseEntity;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * TODO 角色实体类
 *
 * @author Mr.He
 * 2020/3/7 1:30
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Entity
@Getter
@Setter
public class SysRoles extends BaseEntity {
    @Column(columnDefinition = "varchar(64) ")
    private String name;

    @Column(columnDefinition = "longtext comment '菜单Id'")
    private String menusIds;

    @Column(columnDefinition = "longtext comment '权限列表'")
    private String permissionList;
    @Transient
    private List<SysMenus> sysMenus;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}