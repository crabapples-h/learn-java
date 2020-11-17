package other.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * TODO
 *
 * @author Mr.He
 * 2020/11/17 下午3:08
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Getter
@Setter
public class ResponseStatus {
    private List<Extension> Extension;
    private List<String> Errors;
    private String Ack;
    private String Timestamp;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}