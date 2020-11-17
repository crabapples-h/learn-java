package other.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

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
public class Dportinfo {

    private String city;
    private String cityname;
    private String aport;
    private String aportsname;
    private String cityid;
    private String bsname;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}