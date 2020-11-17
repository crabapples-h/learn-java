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
public class Airlines {
    // 航空公司
    private String airsname;
    // 航班号
    private String aircode;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}