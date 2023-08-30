package cn.crabapples.system.entity;

import cn.crabapples.common.base.BaseEntity_Jpa;
import cn.crabapples.common.config.JpaConverterListJson;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@DynamicInsert
@DynamicUpdate
public class SysRoles extends BaseEntity_Jpa {
    @Column(columnDefinition = "varchar(64) ")
    private String name;

    //角色拥有的菜单列表
    @Column(columnDefinition = "longtext comment '菜单Id'")
    @Convert(converter = JpaConverterListJson.class)
    private List<String> menusIds;

    //角色拥有的权限列表
    @Column(columnDefinition = "longtext comment '权限列表'")
    private String permissionList;

//    @Transient
//    private List<SysMenus> sysMenus;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
