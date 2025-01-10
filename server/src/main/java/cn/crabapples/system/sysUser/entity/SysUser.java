package cn.crabapples.system.sysUser.entity;

import cn.crabapples.common.base.BaseEntity;
import cn.crabapples.common.dic.Dict;
import cn.crabapples.common.mybatis.flex.OnInsertListener;
import cn.crabapples.common.mybatis.flex.OnUpdateListener;
import cn.crabapples.system.sysRole.entity.SysRole;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.annotation.JSONField;
import com.mybatisflex.annotation.*;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * TODO 用户实体类
 *
 * @author Mr.He
 * 2019/7/4 14:51
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Accessors(chain = true)
@Table(value = "sys_user", onInsert = OnInsertListener.class, onUpdate = OnUpdateListener.class)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity<SysUser> {
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;
    private String username; // 用户名
    private String password; // 密码
    private String name;// 姓名
    private String mail;// 邮箱
    private String phone; // 电话
    private String avatar;// 头像
    private Integer age;// 年龄
    @Dict(dictCode = "gender")
    private Integer gender; // 性别

    @Column(ignore = true)
    private List<String> roleList;

    @RelationOneToMany(joinTable = "sys_user_roles",
            joinSelfColumn = "user_id", joinTargetColumn = "role_id",
            selfField = "id", targetField = "id")
    @JSONField(serialize = false)
    private List<SysRole> roleListObj;

    // 用户状态标记 0:正常 1:禁用
    private Integer status;

    // 删除标记 (0:正常 1:删除)
    @Column(isLogicDelete = true)
    private Integer delFlag;

    // 租户, 多个用逗号隔开
    private String tenantId;


    public List<String> getRoleList() {
        if (CollectionUtil.isEmpty(this.roleListObj)) {
            return Collections.emptyList();
        }
        return roleListObj.stream().map(SysRole::getId).collect(Collectors.toList());
    }
}
