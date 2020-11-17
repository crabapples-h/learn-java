package other.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author Mr.He
 * 2020/11/17 下午3:00
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Getter
@Setter
public class Craftinfo {

    private String kind;
    private String craft;
    private String cname;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}