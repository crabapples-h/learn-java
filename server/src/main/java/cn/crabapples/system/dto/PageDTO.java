package cn.crabapples.system.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO 分页DTO
 *
 * @author Mr.He
 * 8/29/20 11:47 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Getter
@Setter
public class PageDTO {
    protected int pageSize;
    protected int pageIndex;
    protected int dataCount;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
