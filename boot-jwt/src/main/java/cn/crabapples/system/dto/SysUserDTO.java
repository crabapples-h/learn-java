package cn.crabapples.system.dto;

import cn.crabapples.common.base.BaseEntity;
import cn.crabapples.system.entity.SysUser;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
public class SysUserDTO extends BaseEntity<SysUser> {
    private String id;
    private String username;
    private String name;
    private String mail;
    private String phone;
    private Integer age;
    // 用户状态标记 0:正常 1:禁用
    private Integer status;
    // 创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    private Date createTime;
    // 更新时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss E")
    private Date updateTime;
    //创建人
    private String createBy;
}
