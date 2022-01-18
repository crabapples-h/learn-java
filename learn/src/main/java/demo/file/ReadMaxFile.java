package demo.file;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * TODO 读取超大文本文件
 *
 * @author Mr.He
 * 2021/5/18 4:40
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@Slf4j
public class ReadMaxFile {
    private static final String PATH = "D:/developer/IdeaWorkspace/JavaProject/learning/logs/learn.log";

    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File(PATH);
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            System.err.println(line);
        }
    }
}
