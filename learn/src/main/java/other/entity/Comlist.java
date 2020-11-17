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
public class Comlist {

    private String tip;
    private int type;
    private String mtip;
    private String stip;
    private String info;
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}