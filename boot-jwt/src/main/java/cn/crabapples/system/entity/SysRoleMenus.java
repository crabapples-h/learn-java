package cn.crabapples.system.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@ToString
@NoArgsConstructor
public class SysRoleMenus {
    public SysRoleMenus(SysRole role, List<SysMenu> menusList) {
        this.sysMenus = menusList;
        this.permissionList = role.getPermissionList();
        this.name = role.getName();
        this.setId(role.getId());
    }

    // id 为自增主键
    @Id(keyType = KeyType.Auto)
    private String id;

    private String name;

    //角色拥有的权限列表
    private List<String> permissionList;

    //角色拥有的菜单列表
    private List<SysMenu> sysMenus;
    //角色拥有的菜单列表Id
    private List<String> sysMenuIds;
}
