package demo.file;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

/**
 * TODO 多线程下载文件演示(断点续传)
 *
 * @author Mr.He
 * 2021/5/18 1:00
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class FileDownloadDemo {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FileDownloadDemo.class);
    private int THREAD_COUNT;
    private final int SUCCESS_CODE = 206;
    private final String DEFAULT_NAME;
    private final CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

    /**
     * @param threadCount 下载启动的线程数
     * @param defaultName 设置文件名解析失败时默认使用的文件名
     */
    public FileDownloadDemo(int threadCount, String defaultName) {
        this.THREAD_COUNT = threadCount;
        this.DEFAULT_NAME = defaultName;
    }

    private static final String DOWNLOAD_URL = "http://z6stkux-www-photoshop-com.4pbxyvfbton.fgongbi01.cn" +
            "/5b14839c4fd2ddb4576d3bf8efac639a.RedAlert2_setup.rar" +
            "?ssig=0081e846ba8330aba92364247ba8420f7f851057&time_stamp=1621277920&fn=bd4ae0d1ae367bbbade5f5d8e8a27568";

    public static void main(String[] args) throws IOException, InterruptedException {
//        new FileDownloadDemo(10, "download").start(DOWNLOAD_URL, "d:/");
        HttpURLConnection connection = (HttpURLConnection) new URL(DOWNLOAD_URL).openConnection();
        connection.setConnectTimeout(100000);
        System.err.println(connection.getContentLengthLong());
//        String data = readStream(connection.getInputStream());
//        System.err.println(data);

    }

    private static String readStream(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder data = new StringBuilder();
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            data.append(line);
        }
        reader.close();
        return data.toString();
    }


    /**
     * 开始下载文件
     *
     * @param url  下载地址
     * @param root 保存路径
     */
    public void start(String url, String root) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();
        String path = root + getFileName(url);
        long fileSize = getFileSize();
        long blockSize = fileSize / THREAD_COUNT;
        log.info("开始下载文件:[{}],文件总大小:[{}]", path, fileSize);
        for (int threadNumber = 0; threadNumber < THREAD_COUNT; threadNumber++) {
            downLoadFile(fileSize, blockSize, threadNumber, path);
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        log.info("下载完成,文件:[{}],耗时:[{}]S", path, (endTime - startTime) / 1000);
    }

    /**
     * 获取要下载的文件的大小
     *
     * @return 要下载的文件的大小
     */
    private long getFileSize() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(DOWNLOAD_URL);
        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        return entity.getContentLength();
    }

    /**
     * 启动下载线程
     *
     * @param fileSize     文件总大小
     * @param blockSize    需要下载的文件块大小
     * @param threadNumber 当前是第几个线程
     */
    private void downLoadFile(long fileSize, long blockSize, int threadNumber, String path) {
        new Thread(() -> {
            try {
                String logPath = path + "-" + threadNumber + ".log";
                File logFile = new File(logPath);
                // 计算当前线程需要下载的文件的开始位置
                long startIndex = threadNumber * blockSize;
                // 计算当前线程需要下载的文件的结束位置
                long endIndex = (threadNumber + 1) * blockSize - 1;
                // 检测下载日志文件是否存在(判断是否为继续下载)
                if (logFile.exists() && logFile.length() > 0) {
                    // 读取下载记录文件将上一次下载的记录设置为本次下载开始位置
                    FileInputStream fileInputStream = new FileInputStream(logFile);
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    startIndex = Integer.parseInt(bufferedReader.readLine());
                    bufferedReader.close();
                }
                // 如果当前线程是最后一个线程，则下载剩余的所有数据
                if (threadNumber == THREAD_COUNT - 1) {
                    endIndex = fileSize;
                }
                log.info("当前下载线程:[{}],开始位置:[{}],结束位置:[{}]", Thread.currentThread().getName(), startIndex, endIndex);
                HttpClient client = HttpClientBuilder.create().build();
                HttpGet get = new HttpGet(DOWNLOAD_URL);
                // 设置请求头为Range标识只请求部分数据
                get.setHeader("Range", String.format("bytes=%d-%d", startIndex, endIndex));
                HttpResponse response = client.execute(get);
                // 当请求为只请求部分数据时返回的状态码为206
                if (SUCCESS_CODE == response.getStatusLine().getStatusCode()) {
                    log.info("开始写入缓存文件:[{}]", Thread.currentThread().getName());
                    // 打开对应的文件,路径为path,模式为可读写
                    RandomAccessFile file = new RandomAccessFile(path, "rw");
                    file.setLength(fileSize);
                    // 将文件的指针移动到当前线程下载的开始位置
                    file.seek(startIndex);
                    InputStream inputStream = response.getEntity().getContent();
                    byte[] data = new byte[1024 * 500];
                    long count = 0;
                    for (int index = inputStream.read(data); index != -1; index = inputStream.read(data)) {
                        file.write(data, 0, index);
                        count += index;
                        // 记录写入磁盘的数据块位置，用于继续下载时读取下载进度
                        RandomAccessFile logFileTemp = new RandomAccessFile(logPath, "rwd");
                        logFileTemp.write(String.valueOf(startIndex + count).getBytes(StandardCharsets.UTF_8));
                        logFileTemp.close();
                    }
                    file.close();
                    countDownLatch.countDown();
                    log.info("线程:[{}]下载完成", Thread.currentThread().getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 根据Url获取要下载的文件名
     *
     * @param url 下载地址
     * @return 文件名
     */
    private String getFileName(String url) {
        int start = url.lastIndexOf("/");
        int end = url.indexOf("?");
        // 解析url中最后一个/到?之间的内容作为文件名
        if (start != -1 && end != -1) {
            String name = url.substring(start + 1, end);
            log.info("需要下载的文件名为:[{}]", name);
            return name;
        }
        // 如果文件名解析失败则返回默认文件名
        return DEFAULT_NAME;
    }
}
