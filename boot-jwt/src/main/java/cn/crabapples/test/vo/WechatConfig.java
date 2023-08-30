package cn.crabapples.test.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WechatConfig {
    private String appId;
    private String timestamp;
    private String nonceStr;
    private String sign;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
