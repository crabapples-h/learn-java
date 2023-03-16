package demo.net;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class Download {

    private static String url = "https://download.jetbrains.com.cn/idea/ideaIU-2022.3.3.exe";

    private static String getTempDir() {
        Properties properties = System.getProperties();
        System.err.println(properties.getProperty("tmpdir"));
        String tmpdir = properties.getProperty("java.io.tmpdir");
        System.err.println(properties.getProperty("user.home"));
//        return tmpdir;
        return "d:/1/";
    }

    private static long getFileSize(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setConnectTimeout(100000);
        long fileSize = connection.getContentLengthLong();
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        System.err.println(headerFields);
        return fileSize;
    }

    private static void download(int threadCount) throws IOException {
        long fileSize = getFileSize(url);
        String fileName = UUID.randomUUID().toString();
        String path = getTempDir() + fileName;
//        String path = System.getProperties().getAbsolutePath() + "/" + fileName;
        long blockSize = fileSize / threadCount;
        for (int i = 0; i < threadCount; i++) {
            int threadIndex = i;
            Thread thread = new Thread(() ->    {
                try {
                    long startIndex = threadIndex * blockSize;
                    long endIndex = (threadIndex + 1) * blockSize;
                    if (threadIndex == threadCount - 1) {
                        endIndex = fileSize;
                    }
                    String logFile = getTempDir() + Thread.currentThread().getName() + ".log";
                    File file = new File(logFile);
                    //判断日志文件是否存在
                    if (file.exists() && file.length() > 0) {
                        // 如果存在，则读取下载日志文件，将上一次下载的记录设置为本次下载开始位置
                        FileInputStream fileInputStream = new FileInputStream(file);
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        startIndex = Integer.parseInt(bufferedReader.readLine());
                        bufferedReader.close();
                    }
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestProperty("Range", String.format("bytes=%d-%d", startIndex, endIndex));
                    connection.setConnectTimeout(100000);
                    if (206 == connection.getResponseCode()) {
                        readStream(path, connection.getInputStream(), startIndex);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.setName("thread-" + threadIndex);
            thread.start();
        }
    }


    private static void readStream(String path, InputStream inputStream, long startIndex) throws IOException {
        String tempPath = getTempDir() + Thread.currentThread().getName() + ".log";
        RandomAccessFile file = new RandomAccessFile(path, "rw");
        file.seek(startIndex);
        byte[] data = new byte[1024 * 500];
        long count = 0;


        for (int i = inputStream.read(data); i != -1; i = inputStream.read(data)) {
            file.write(data, 0, i);
            RandomAccessFile temp = new RandomAccessFile(tempPath, "rwd");
            count += i;
            temp.write(String.valueOf(count + startIndex).getBytes(StandardCharsets.UTF_8));
            temp.close();
        }
        inputStream.close();
        file.close();
    }

    public static void main(String[] args) throws IOException {
        Properties properties = System.getProperties();
        System.err.println(properties.getProperty("java.io.tmpdir"));
        System.err.println(properties.getProperty("user.home"));
        download(2);
    }
}
