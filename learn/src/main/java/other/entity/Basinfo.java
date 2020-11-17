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
public class Basinfo {

    private String flgno;
    private String airsname;
    private String aircode;
    private boolean ishared;
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}