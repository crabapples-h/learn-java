package cn.crabapples.test.entity;

import cn.crabapples.common.base.BaseEntity_Jpa;
import com.alibaba.fastjson.JSONObject;
import java.io.Serializable;

import lombok.*;

@Getter
@Setter
public class SysUser extends BaseEntity_Jpa implements Serializable {
    private String id;

    private Integer age;

    private String name;

    private String password;

    private Boolean status;

    private String username;

    private Boolean role;

    private String mail;

    private String lastModifiedBy;

    private String phone;

    private String rolesList;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}