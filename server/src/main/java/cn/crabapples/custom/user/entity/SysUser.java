package cn.crabapples.custom.user.entity;

import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUser {

    private Integer test;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

