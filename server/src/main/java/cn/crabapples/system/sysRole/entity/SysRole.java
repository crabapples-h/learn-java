package cn.crabapples.system.sysRole.entity;

import cn.crabapples.common.base.BaseEntity;
import cn.crabapples.common.dic.Dict;
import cn.crabapples.system.sysMenu.entity.SysMenu;
import com.alibaba.fastjson2.annotation.JSONField;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
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
@Table("sys_role")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data(staticConstructor = "create")
public class SysRole extends BaseEntity<SysRole> {
    // id 为自增主键
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;
    private String name;// 名称
    private String code;// 角色

    //角色拥有的菜单列表
    @Column(ignore = true)
    private List<SysMenu> menuList;

    //角色拥有的权限列表
//    private List<String> permissionList;

    // 租户, 多个用逗号隔开
    private String tenantId;
}
