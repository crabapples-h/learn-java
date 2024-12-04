package cn.crabapples.system.sysUser.entity;

import cn.crabapples.common.base.BaseEntity;
import cn.crabapples.common.dic.Dict;
import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID;

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
@TableName(value = "sys_user")
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity<SysUser> {
    @TableId(type = ASSIGN_UUID)
    private String id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 姓名
    private String name;
    // 邮箱
    private String mail;
    // 电话
    private String phone;
    // 头像
    private String avatar;
    // 年龄
    private Integer age;
    // 年龄
    @Dict(dictCode = "gender")
    private Integer gender;

    // 最后操作用户
    private String updateBy;

    @TableField(exist = false)
    private List<String> roleList;

    // 用户状态标记 0:正常 1:禁用
    private Integer status;

    // 创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 删除标记 (0:正常 1:删除)
    @TableLogic
    private Integer delFlag;

    //创建人
    private String createBy;
}
