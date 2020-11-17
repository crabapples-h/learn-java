package other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

/**
 * TODO
 *
 * @author Mr.He
 * 2020/11/16 下午3:45
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class GetFlyInfo {
    public static Logger logger = LoggerFactory.getLogger(GetFlyInfo.class);
    public static void main(String[] args) throws IOException {
        byte[] tempData = new byte[1024];
        System.err.println("地址:"+new File("param.json").getAbsolutePath());
        FileInputStream fileInputStream = new FileInputStream(new File("param.json"));
        String urlString = "https://m.ctrip.com/restapi/soa2/14022/flightListSearch?_fxpcqlniredt=09031091112268009574";
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        OutputStream outputStream = connection.getOutputStream();
        for (int i = 0; i != -1; i = Objects.requireNonNull(fileInputStream).read(tempData)) {
            outputStream.write(tempData);
        }
        outputStream.flush();
        outputStream.close();
        InputStream inputStream = connection.getInputStream();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i != -1; i = inputStream.read(tempData)) {
            result.append(new String(tempData));
        }
        logger.info(String.valueOf(result));
        JSONObject jsonObject = JSON.parseObject(String.valueOf(result));
        System.err.println(jsonObject);
    }
}
