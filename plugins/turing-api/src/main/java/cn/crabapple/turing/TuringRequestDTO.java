package cn.crabapple.turing;

import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO 图灵机器人api接口请求参数
 *
 * @author Mr.He
 * @date 2019/7/23 21:17
 * e-mail wishforyou.xia@gmail.com
 * pc-name 29404
 */
@Getter
@Setter
public class TuringRequestDTO {
    private String reqType;
    private Perception perception;
    private UserInfo userInfo;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    @Getter
    @Setter
    public static class Perception {
        private InputText inputText;
        private InputImage inputImage;
        private SelfInfo selfInfo;

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }

    @Getter
    @Setter
    public static class InputText {
        private String text;

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }

    @Getter
    @Setter
    public static class InputImage {
        private String url;

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }

    @Getter
    @Setter
    public static class SelfInfo {
        private Location location;

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }

    @Getter
    @Setter
    public static class Location {
        private String city;
        private String province;
        private String street;

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }

    @Getter
    @Setter
    public static class UserInfo {
        private String apiKey;
        private String userId;

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }
}
