package cn.crabapples.common.utils.turing;

import com.alibaba.fastjson.JSONObject;

/**
 * TODO 图灵机器人api接口请求参数
 *
 * @author Mr.He
 * @date 2019/7/23 21:17
 * e-mail wishforyou.xia@gmail.com
 * pc-name 29404
 */
public class TuringRequestDTO {
    private String reqType;
    private Perception perception;
    private UserInfo userInfo;

    public String getReqType() {
        return reqType;
    }
    public void setReqType(String reqType) {
        this.reqType = reqType;
    }
    public Perception getPerception() {
        return perception;
    }
    public void setPerception(Perception perception) {
        this.perception = perception;
    }
    public UserInfo getUserInfo() {
        return userInfo;
    }
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public static class Perception{
        private InputText inputText;
        private InputImage inputImage;
        private SelfInfo selfInfo;
        public InputText getInputText() {
            return inputText;
        }
        public void setInputText(InputText inputText) {
            this.inputText = inputText;
        }
        public InputImage getInputImage() {
            return inputImage;
        }
        public void setInputImage(InputImage inputImage) {
            this.inputImage = inputImage;
        }
        public SelfInfo getSelfInfo() {
            return selfInfo;
        }
        public void setSelfInfo(SelfInfo selfInfo) {
            this.selfInfo = selfInfo;
        }
        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }

    public static class InputText{
        private String text;
        public String getText() {
            return text;
        }
        public void setText(String text) {
            this.text = text;
        }
        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }

    public static class InputImage{
        private String url;
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }

    public static class SelfInfo{
        private Location location;
        public Location getLocation() {
            return location;
        }
        public void setLocation(Location location) {
            this.location = location;
        }
        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }

    public static class Location{
        private String city;
        private String province;
        private String street;
        public String getCity() {
            return city;
        }
        public void setCity(String city) {
            this.city = city;
        }
        public String getProvince() {
            return province;
        }
        public void setProvince(String province) {
            this.province = province;
        }
        public String getStreet() {
            return street;
        }
        public void setStreet(String street) {
            this.street = street;
        }
        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }

    public static class UserInfo{
        private String apiKey;
        private String userId;
        public String getApiKey() {
            return apiKey;
        }
        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
        public String getUserId() {
            return userId;
        }
        public void setUserId(String userId) {
            this.userId = userId;
        }
        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }


}