package cn.crabapples.common.utils.turing;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * TODO 图灵机器人api接口返回参数
 *
 * @author Mr.He
 * @date 2019/7/23 21:30
 * e-mail wishforyou.xia@gmail.com
 * pc-name 29404
 */
@Getter
@Setter
public class TuringResponseDTO {
    private Intent intent;
    private List<Results> results;

    @Getter
    @Setter
    public static class Intent {
        private String code;
        private String intentName;
        private String actionName;
        private Parameters parameters;

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }

    @Getter
    @Setter
    public static class Parameters {
        private String nearby_place;

        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }

    @Getter
    @Setter
    public static class Results {
        private String groupType;
        private String resultType;
        private Values values;

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }

    @Getter
    @Setter
    public static class Values {
        private String text;

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }

    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
