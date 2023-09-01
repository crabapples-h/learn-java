package cn.crabapples.system.entity;

import cn.crabapples.common.Dict;
import cn.crabapples.common.base.BaseEntity;
import com.alibaba.fastjson.JSONObject;
import com.mybatisflex.annotation.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

/**
 * TODO 用户实体类
 *
 * @author Mr.He
 * 2019/7/4 14:51
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Getter
@Setter
public class SysUser extends BaseEntity {

    // 用户名
    private String username;

    // 密码
    private String password;

    // 姓名
    @Dict(dictCode = "test")
    private String name;

    // 邮箱
    private String mail;

    // 电话
    private String phone;

    // 年龄
    private Integer age;

    @LastModifiedBy
    // 最后操作用户
    private String lastModifiedBy;

    @Column
    private List<String> rolesList;

    // 用户状态标记 0:正常 1:禁用
    private Integer status;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
