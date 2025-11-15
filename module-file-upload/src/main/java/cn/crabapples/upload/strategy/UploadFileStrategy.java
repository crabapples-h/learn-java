package cn.crabapples.upload.strategy;

import javax.servlet.http.HttpServletRequest;
import java.io.OutputStream;

public interface UploadFileStrategy {
    String upload(HttpServletRequest request);

    void download(String bucket, String fileName, OutputStream outputStream);

    String share(String bucket, String fileName);

    void remove(String fileName);
}
