package cn.crabapples.upload.strategy;

import cn.crabapples.minio.config.MinioConfigProperties;
import cn.crabapples.minio.service.MinioService;
import cn.crabapples.upload.entity.FileInfo;
import cn.crabapples.upload.service.FileInfoService;
import cn.crabapples.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component("upload_minio")
@Slf4j
public class UploadMinioStrategy implements UploadFileStrategy {
    @Lazy
    @Resource
    private FileInfoService fileInfoService;

    private final MinioService service;

    public UploadMinioStrategy(MinioService service) {
        this.service = service;
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String upload(HttpServletRequest request) {
        log.info("开始上传文件");
        Collection<Part> files = fileInfoService.getFileParts(request);
        List<FileInfo> list = files.stream().map(part -> {
            String fileName = part.getSubmittedFileName();
            String contentType = part.getContentType();
            try {
                InputStream inputStream = part.getInputStream();
                String randomFileName = StringUtils.genRandomFileName(fileName);
                service.uploadFile(randomFileName, inputStream);
                MinioConfigProperties config = service.getConfig();
                String fullPath = config.getBucketName() + "/" + randomFileName;
                return new FileInfo()
                        .setVirtualPath(fullPath)
                        .setContentType(contentType)
                        .setFileSize(part.getSize())
                        .setOldName(fileName)
                        .setSaveType(UPLOAD_TYPE_ENUM.MINIO.type);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        String url = fileInfoService.saveFileInfo(list).stream()
                .map(e -> "/api/file/download/" + UPLOAD_TYPE_ENUM.MINIO + "/" + e.getVirtualPath())
                .collect(Collectors.joining(","));
        log.info("文件上传完成url:[{}]", url);
        return url;
    }

    @Override
    public void download(String bucket, String fileName, OutputStream outputStream) {
        service.downloadAsStream(bucket, fileName, outputStream);
    }

    @Override
    public String share(String bucket, String fileName) {
        return service.createTempDownloadUrl(bucket, fileName, 1, TimeUnit.HOURS);
    }

    @Override
    public void remove(String fileName) {
        service.remove(fileName);
    }
}
