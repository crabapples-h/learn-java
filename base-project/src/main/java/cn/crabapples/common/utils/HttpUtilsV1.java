package cn.crabapples.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * TODO Http请求工具类
 *
 * @author Mr.He
 * 2019/11/14 21:27
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public class HttpUtilsV1 {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtilsV1.class);
    private static final Map<String, String> HEADER = new HashMap<>();
    private static final int CONN_TIMEOUT = 30000;    //链接超时时间
    private static final int READ_TIMEOUT = 30000;    //响应超时时间

    /*
     * 默认请求头
     */
    static {
        HEADER.put("Accept", "*/*");
        HEADER.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        HEADER.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.26 Safari/537.36 Core/1.63.6799.400 QQBrowser/10.3.2908.400");
    }

    /**
     * 执行请求操作
     *
     * @param url    请求地址
     * @param params 参数
     * @param method 请求方式
     * @return 回结果
     */
    private static String sendRequest(String url, String params, String method) {
        logger.info("开始发送请求,url:[{}],参数[{}],请求方式[{}]", url, params, method);
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            setRequestHeader(conn, HEADER);
            logger.debug("设置请求方式为:[{}]", method);
            conn.setRequestMethod(method);
            if (null != params) {
                logger.debug("开始发送参数:[{}]", params);
                out = new PrintWriter(conn.getOutputStream());
                out.print(params);
                out.flush();
            }
            logger.debug("参数发送完成");
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            logger.debug("开始接收返回值");
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            logger.debug("返回值接受完成:[{}]", result.toString());
        } catch (Exception e) {
            logger.error("发送[{}]请求出现异常！参数:[]", method,e);
        } finally {
            logger.debug("即将关闭输入输出流");
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                logger.error("关闭输入输出流时出现异常:[]", ex);
            }
        }
        return result.toString();
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
     * 拼接请求参数
     *
     * @param url      请求地址
     * @param paramMap 需要传递的参数
     * @return 拼接之后的参数
     */
    private static Map<String, String> getParam(String url, Map<String, Object> paramMap) {
        logger.debug("开始拼接参数:[{}],[{}]", url, paramMap);
        Set<String> keySet = paramMap.keySet();
        String param = "";
        for (String string : keySet) {
            logger.debug("当前正在拼接的参数:[{}],[{}]", string, paramMap.get(string));
            param = param + string + "=" + paramMap.get(string) + "&";
        }
        param = param.substring(0, param.length() - 1);
        String allUrl = url + "?" + param;
        Map<String, String> par = new HashMap<String, String>();
        par.put("url", url);
        par.put("param", param);
        par.put("allUrl", allUrl);
        logger.debug("参数拼接完成:[{}],", par);
        return par;
    }

    /**
     * 发送POST请求
     *
     * @param url 请求地址
     * @param map 参数
     * @return 返回结果
     */
    public static String postRequest(String url, Map map) {
        Map<String, String> param = getParam(url, map);
        logger.debug("即将开始发送POST请求,url:[{}],参数:[{}]", url, param);
        String result = sendRequest(param.get("url"), param.get("param"), "POST");
        logger.debug("请求结束,返回值:[{}]", result);
        return result;
    }

    /**
     * 发送POST请求
     *
     * @param url 请求地址
     * @return 返回结果
     */
    public static String postRequest(String url) {
        logger.debug("即将开始发送POST请求(无参数),url:[{}]", url);
        String result = sendRequest(url, null, "POST");
        logger.debug("请求结束,返回值:[{}]", result);
        return result;
    }

    /**
     * 发送GET请求
     *
     * @param url 请求地址
     * @param map 参数
     * @return 返回结果
     */
    public static String getRequest(String url, Map map) {
        Map<String, String> param = getParam(url, map);
        logger.debug("即将开始发送GET请求,url:[{}],参数:[{}]", url, param);
        String result = sendRequest(param.get("url"), param.get("param"), "GET");
        logger.debug("请求结束,返回值:[{}]", result);
        return result;
    }

    /**
     * 发送GET请求
     *
     * @param url 请求地址
     * @return 返回结果
     */
    public static String getRequest(String url) {
        logger.debug("即将开始发送GET请求(无参数),url:[{}]", url);
        String result = sendRequest(url, null, "GET");
        logger.debug("请求结束,返回值:[{}]", result);
        return result;
    }
}
