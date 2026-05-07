package cn.crabapples.system.dto;

import cn.crabapples.common.base.BaseEntity;
import cn.crabapples.common.annotation.Dict;
import cn.crabapples.system.sysUser.entity.SysUser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * TODO 用户基本信息DTO
 *
 * @author Mr.He
 * 2021/4/12 8:26
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@Getter
@Setter
@Accessors(chain = true)
public class SysUserDTO extends BaseEntity<SysUser> {
    private String id;
    private String username;
    private String name;
    private String mail;
    @Dict(dictCode = "gender")
    private Integer gender;
    private String phone;
    private String avatar;
    private Integer age;
    // 用户状态标记 0:正常 1:禁用
    private Integer status;
}
