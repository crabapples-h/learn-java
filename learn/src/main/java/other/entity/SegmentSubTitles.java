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
public class SegmentSubTitles {
    private List<SubTitles> subTitles;
    private int segment;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}