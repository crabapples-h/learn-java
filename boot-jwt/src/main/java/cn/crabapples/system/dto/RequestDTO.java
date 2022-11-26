package cn.crabapples.system.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO 请求DTO
 *
 * @author Mr.He
 * 8/30/20 10:59 AM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Getter
@Setter
public class RequestDTO extends PageDTO {
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
