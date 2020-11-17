package other.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * TODO
 *
 * @author Mr.He
 * 2020/11/17 下午3:02
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Getter
@Setter
public class JsonRootBean {
    private List<String> recomd;
    private int segno;
    private ResponseStatus ResponseStatus;
    private int dfcnt;
    private String rltmsg;
    private int rstype;
    private List<Airlines> airlines;
    private List<Fltitem> fltitem;
    private int rlt;
    private List<Airports> airports;
    private boolean hastab;

    public JsonRootBean() {
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}