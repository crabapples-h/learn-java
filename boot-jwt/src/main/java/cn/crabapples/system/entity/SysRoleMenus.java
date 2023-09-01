package cn.crabapples.system.entity;

import cn.crabapples.common.Dict;
import cn.crabapples.common.base.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
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
@ToString
public class SysRoleMenus extends BaseEntity {
    public SysRoleMenus(SysRoles role, List<SysMenus> menusList) {
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
    private List<SysMenus> sysMenus;

    // 创建时间
    @CreatedDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    // 更新时间
    @LastModifiedDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    @Column(onUpdateValue = "now()", onInsertValue = "now()")
    private LocalDateTime updateTime;

    // 删除标记 (0:正常 1:删除)
    @Column(isLogicDelete = true)
    @Dict(dictCode = "delFlag")
    private Integer delFlag;

    //创建人
    @CreatedBy
    private String createBy;
}
