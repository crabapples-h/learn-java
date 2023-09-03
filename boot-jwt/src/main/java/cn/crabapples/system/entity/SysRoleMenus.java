package cn.crabapples.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID;

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
    // id 为自增主键
    @TableId(type = ASSIGN_UUID)
    private String id;

    private String name;

    //角色拥有的权限列表
//    private List<String> permissionList;

    //角色拥有的菜单列表
    private List<SysMenu> sysMenus;
    //角色拥有的菜单列表Id
    private List<String> sysMenuIds;
}
