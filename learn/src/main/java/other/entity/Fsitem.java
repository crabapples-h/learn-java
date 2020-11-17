package other.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
public class Fsitem {
    private String city;
    private Date deptime;
    private String cityCode;
    private String airportCode;
    private Date arrtime;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}