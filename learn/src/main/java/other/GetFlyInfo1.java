package other;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import other.entity.Fltitem;
import other.entity.JsonRootBean;
import other.entity.Mutilstn;
import other.entity.Policyinfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Mr.He
 * 2020/11/16 下午3:45
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class GetFlyInfo1 {
    public static Logger logger = LoggerFactory.getLogger(GetFlyInfo1.class);
    private static final String urlString = "https://m.ctrip.com/restapi/soa2/14022/flightListSearch?_fxpcqlniredt=09031091112268009574";

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("param.json"));
        File file = new File("param.json");
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(file, MediaType.parse("application/json"));
        Request request = new Request.Builder().post(requestBody).url(urlString).build();
        Response response = client.newCall(request).execute();
        logger.info("response:[{}]", response);
        assert response.body() != null;
        String result = response.body().string();
        logger.info("body:[{}]", result);
        JsonRootBean jsonRootBean = JSONObject.parseObject(result, JsonRootBean.class);
        System.err.println(jsonRootBean);
        List<Fltitem> fltitem = jsonRootBean.getFltitem().stream().filter(e -> e.getMutilstn().size() == 1).filter((e) -> "多彩航空".equals(e.getMutilstn().get(0).getBasinfo().getAirsname())).collect(Collectors.toList());
        fltitem.forEach(e -> {
            List<Mutilstn> mutilstns = e.getMutilstn();
            List<Policyinfo> policyinfo = e.getPolicyinfo();
            mutilstns.forEach(r -> {
                System.out.println(r);
            });
            policyinfo.forEach(r->{
                System.out.println(r);
            });
        });

    }
}
