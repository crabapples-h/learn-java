package cn.crabapples.system.dto;

import cn.crabapples.system.entity.SysRoles;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
public class SysUserDTO {
    private String id;
    private String username;
    private String name;
    private int age;
    private String mail;
    private String phone;
    private int status;
    private List<SysRoles> rolesList;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
