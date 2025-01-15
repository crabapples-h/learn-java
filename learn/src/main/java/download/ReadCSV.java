package download;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadCSV {
    public static void main(String[] args) {
        String filePath = "/Users/mshe/developer/idea-workspaces/learn-java/learn/src/main/java/download/export_urls.csv"; // CSV 文件路径
        String line;
        String delimiter = ","; // 分隔符
        Map<String, String> files = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                // 将行按分隔符切分
                String[] values = line.split(delimiter);
                files.put(values[0], values[1]);
//                for (String value : values) {
//                    System.out.print(value + " ");
//                    files.put(value, value);
//                }
                System.out.println(); // 换行显示下一行内容
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> read() {
        Map<String, String> files = new HashMap<>();
        String filePath = "/Users/mshe/developer/idea-workspaces/learn-java/learn/src/main/java/download/export_urls.csv"; // CSV 文件路径
        String line;
        String delimiter = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter);
                files.put(values[0], values[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }
}
