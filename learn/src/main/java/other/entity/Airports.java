package other.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author Mr.He
 * 2020/11/17 下午2:59
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Getter
@Setter
public class Airports {
    // 机场代码
    private String aport;
    // 机场名称
    private String aportsname;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}