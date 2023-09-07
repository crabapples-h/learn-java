package cn.crabapples.system.entity;

import cn.crabapples.common.dic.Dict;
import cn.crabapples.common.base.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID;

/**
 * TODO 角色实体类
 *
 * @author Mr.He
 * 2020/3/7 1:30
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@TableName("sys_role")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data(staticConstructor = "create")
public class SysRole extends BaseEntity<SysRole> {
    // id 为自增主键
    @TableId(type = ASSIGN_UUID)
    private String id;

    // 名称
    private String name;

    //角色拥有的菜单列表
    @TableField(exist = false)
    private List<SysMenu> menuList;

    //角色拥有的权限列表
//    private List<String> permissionList;

    // 创建时间
    @CreatedDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    // 更新时间
    @LastModifiedDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    // 删除标记 (0:正常 1:删除)
    @TableLogic
    @Dict(dictCode = "delFlag")
    private Integer delFlag;

    //创建人
    @CreatedBy
    private String createBy;


//    @Transient
//    private List<SysMenus> sysMenus;
}
