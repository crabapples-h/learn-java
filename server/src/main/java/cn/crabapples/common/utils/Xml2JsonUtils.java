package cn.crabapples.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * TODO xml转json工具类
 *
 * @author Mr.He
 * 2020/7/25 14:16
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
public class Xml2JsonUtils {
    public static JSONObject xml2JsonObject(InputStream inputStream) {
        JSONObject jsonObject = new JSONObject();
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document doc = saxBuilder.build(inputStream);
            Element root = doc.getRootElement();
            jsonObject.put(root.getName(), iterateElement(root));
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private static Map<String, Object> iterateElement(Element element) {
        List<Element> children = element.getChildren();
        Map<String, Object> data = new HashMap<>();
        for (Element value : children) {
            List<Object> list = new LinkedList<>();
            if (value.getTextTrim().equals("")) {
                if (value.getChildren().size() == 0)
                    continue;
                if (data.containsKey(value.getName())) {
                    list = Lists.newArrayList(data.get(value.getName()));
                }
                list.add(iterateElement(value));
            } else {
                if (data.containsKey(value.getName())) {
                    list = Lists.newArrayList(data.get(value.getName()));
                }
                list.add(value.getTextTrim());
            }
            data.put(value.getName(), list);
        }
        return data;
    }
}
