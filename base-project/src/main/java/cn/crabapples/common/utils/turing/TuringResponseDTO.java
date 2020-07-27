package cn.crabapples.common.utils.turing;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * TODO 图灵机器人api接口返回参数
 *
 * @author Mr.He
 * @date 2019/7/23 21:30
 * e-mail wishforyou.xia@gmail.com
 * pc-name 29404
 */
public class TuringResponseDTO {
    private Intent intent;
    private List<Results> results;

    public Intent getIntent() {
        return intent;
    }
    public void setIntent(Intent intent) {
        this.intent = intent;
    }
    public List<Results> getResults() {
        return results;
    }
    public void setResults(List<Results> results) {
        this.results = results;
    }

    public static class Intent{
        private String code;
        private String intentName;
        private String actionName;
        private Parameters parameters;
        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }
        public String getIntentName() {
            return intentName;
        }
        public void setIntentName(String intentName) {
            this.intentName = intentName;
        }
        public String getActionName() {
            return actionName;
        }
        public void setActionName(String actionName) {
            this.actionName = actionName;
        }
        public Parameters getParameters() {
            return parameters;
        }
        public void setParameters(Parameters parameters) {
            this.parameters = parameters;
        }
        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }
    public static class Parameters{
        private String nearby_place;
        public String getNearby_place() {
            return nearby_place;
        }
        public void setNearby_place(String nearby_place) {
            this.nearby_place = nearby_place;
        }
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }
    public static class Results{
        private String groupType;
        private String resultType;
        private Values values;
        public String getGroupType() {
            return groupType;
        }
        public void setGroupType(String groupType) {
            this.groupType = groupType;
        }
        public String getResultType() {
            return resultType;
        }
        public void setResultType(String resultType) {
            this.resultType = resultType;
        }
        public Values getValues() {
            return values;
        }
        public void setValues(Values values) {
            this.values = values;
        }
        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }
    public static class Values{
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
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
