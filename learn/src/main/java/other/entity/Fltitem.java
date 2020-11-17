package other.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * TODO
 *
 * @author Mr.He
 * 2020/11/17 下午3:07
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Getter
@Setter
public class Fltitem {
    private List<Policyinfo> policyinfo;
    // dangridaoda
    private int fltoday;
    private List<Mutilstn> mutilstn;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}