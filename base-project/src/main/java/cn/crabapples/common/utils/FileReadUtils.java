package cn.crabapples.common.utils;


import cn.crabapples.common.ApplicationException;

import java.io.*;

/**
 * TODO 读取文件内容工具类
 *
 * @author Mr.He
 * 2021/5/18 17:41
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class FileReadUtils {
    public static String read(String fileName) {
        try {
            InputStream inputStream = FileReadUtils.class.getResourceAsStream(fileName);
            if (null == inputStream) {
                throw new ApplicationException("文件不存在");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder content = new StringBuilder();
            for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                content.append(temp);
            }
            reader.close();
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
