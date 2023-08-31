package cn.crabapples.system.entity;

import cn.crabapples.common.base.BaseEntity_Mybatis;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * TODO 角色菜单实体类
 *
 * @author Ms.He
 * 2023/8/31 23:40
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Getter
@Setter
@NoArgsConstructor
public class SysRoleMenus extends BaseEntity_Mybatis {
    private String name;

    //角色拥有的权限列表
    private List<String> permissionList;

    //角色拥有的菜单列表
    private List<SysMenus> sysMenus;

    public SysRoleMenus(SysRoles role, List<SysMenus> menusList) {
        this.sysMenus = menusList;
        this.permissionList = role.getPermissionList();
        this.name = role.getName();
        this.setId(role.getId());
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
