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
public class Mutilstn {
    private String tnote;
    private Aportinfo aportinfo;
    private List<Fsitem> fsitem;
    private Craftinfo craftinfo;
    private Dateinfo dateinfo;
    private List<Comlist> comlist;
    private boolean isstop;
    private Dportinfo dportinfo;
    private Basinfo basinfo;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}