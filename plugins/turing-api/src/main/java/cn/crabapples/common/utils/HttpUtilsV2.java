package cn.crabapples.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * 网络请求工具类
 * 2019年1月8日 下午2:57:52
 *
 * @author H
 * TODO 发起网络请求时使用
 * Admin
 */
public class HttpUtilsV2 {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtilsV2.class);
    private static final Map<String, String> HEADER = new TreeMap<String, String>();
    private static final int CONN_TIMEOUT = 30000;    //链接超时时间
    private static final int READ_TIMEOUT = 30000;    //响应超时时间

    /**
     * 默认请求头
     */
    static {
        HEADER.put("Accept", "*/*");
        HEADER.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        HEADER.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.26 Safari/537.36 Core/1.63.6799.400 QQBrowser/10.3.2908.400");
    }

    /**
     * 发送Http请求(可自定义请求头)
     *
     * @param url    请求地址
     * @param params 请求参数
     * @param header 请求头(若不设置则使用默认请求头)
     * @return 返回响应结果
     */
    public static String sendHttpRequest(String url, String params, RequestMethod method, Map<String, String>... header) throws IOException {
        logger.debug("即将准备发送请求,url:[{}],参数[{}],请求方式[{}],请求头:[{}]", url, params, method, header);
        Map<String,String> map = new HashMap<>();
        Stream.of(header).forEach(e -> e.keySet().forEach(r -> map.put(r, e.get(r))));
        String result = sendRequest(url, params, method, map);
        logger.debug("请求结束,结果:[{}]", result);
        return result;
    }

    /**
     * 设置请求头
     *
     * @param conn 传入需要设置请求头的HttpURLConnection
     * @return 返回设置完毕之后的HttpURLConnection
     */
    private static HttpURLConnection setRequestHeader(HttpURLConnection conn, Map<String, String> header) {
        logger.debug("开始设置请求头:[{}]", header);
        if (null != header || header.size() > 0) {
            Set<String> keySet = HEADER.keySet();
            for (String key : keySet) {
                conn.setRequestProperty(key, HEADER.get(key));
            }
        } else {
            logger.debug("使用默认请求头:[{}]", header);
            Set<String> keySet = header.keySet();
            for (String key : keySet) {
                conn.setRequestProperty(key, header.get(key));
            }
        }
        logger.debug("请求头设置完毕");
        return conn;
    }

    /**
     * 发送http请求
     *
     * @param url    请求地址
     * @param params 参数
     * @param header 请求头
     * @return 响应消息
     */
    private static String sendRequest(String url, String params, RequestMethod method, Map<String, String> header) throws IOException {
        logger.info("开始发送请求,url:[{}],参数[{}],请求方式[{}],请求头:[{}]", url, params, method, header);
        PrintWriter print = null;
        BufferedReader reader = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            logger.debug("设置请求方式");
            if (method == RequestMethod.POST) {
                conn.setRequestMethod("POST");
            } else {
                conn.setRequestMethod("GET");
            }
            logger.debug("请求方式设置为:[{}]",conn.getRequestMethod());
            conn.setDoOutput(true);
            conn.setDoInput(true);
            setRequestHeader(conn, header);
            conn.setConnectTimeout(CONN_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
            logger.debug("准备发送参数:[{}]", params);
            print = new PrintWriter(osw);
            print.print(params);
            print.flush();
            print.close();
            logger.debug("参数发送完成");
            InputStream in = conn.getInputStream();
            StringBuilder result = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(in));
            logger.debug("开始接收返回值");
            while (true) {
                String str = reader.readLine();
                if (null == str) {
                    break;
                }
                result.append(str);
            }
            logger.debug("返回值接受完成:[{}]", result.toString());
            in.close();
            logger.info("请求结束,返回结果:[{}]", result.toString());
            return result.toString();
        } catch (IOException e) {
            logger.error("请求失败,详情:[]", e);
            throw e;
        }
    }

}