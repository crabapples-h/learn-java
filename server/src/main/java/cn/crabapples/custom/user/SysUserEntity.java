package cn.crabapples.custom.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SysUser {

    private Integer  test;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

