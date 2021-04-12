package cn.crabapples.system.dto;

import cn.crabapples.common.base.BaseDTO;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

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
public class SysUserDTO extends BaseDTO {
    private String username;
    private String name;
    private int age;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
