//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.http.client.methods.HttpPost;
//
//import java.util.Arrays;
//import java.util.Date;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//public class wx {
//    public static void main(String[] args) {
//
//    }
//
//    private void getQrCode() {
//        final HttpPost httpPost = new HttpPost();
//    }
//
//    private long createTimestamp() {
//        return (long) (new Date().getTime() / 1e3);
//    }
//
//    private String createNonceStr() {
//        String zfcAll = "abcdefghijklmnopqrstuvwxyz1234567890";
//        return RandomStringUtils.random(32, zfcAll);
//    }
//
//    private String createSign(Map<String, Object> body, String url, String token, long timestamp, String nonceStr) {
//        String params = "";
//        Set<String> keys = body.keySet();
//        for (String key : keys) {
//            Object value = body.get(key);
//            if (null != value && value instanceof Map) {
//                String str = Arrays.stream(JSONObject.toJSONString(value).split("")).sorted().collect(Collectors.joining(""));
//                params += key + "=" + str + "&";
//            } else {
//                if (0 == value || value) {
//                    params += key + "=" + body.get(key).toString() + "&";
//                } else {
//                    body.se = ""
//                    params += key + "=&"
//                }
//            }
//        }
//        for (int index = 0; index < keys.size(); index++) {
//            String key = keys;
//            Object value = body[key];
//            if ("object" == _typeof(value) && null != = value) {
//                let str = JSON.stringify(body[key]).split("").sort().join("");
//                params += key + "=" + str + "&"
//            } else {
//                if (0 == value || value) {
//                    params += key + "=" + body[key] + "&"
//                } else {
//                    body[key] = ""
//                    params += key + "=&"
//                }
//            }
//        }
//        params += "url=" + url + "&"
//        params += token ? "accessToken=" + token + "&" : ""
//        params += "timestamp=" + timestamp + "&"
//        params += "nonceStr=" + nonceStr + "&"
//        params += "key=" + KEY
//        return md5(params)
//    },
//}
