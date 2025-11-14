package cn.crabapples.system.sysFile.strategy;

import cn.crabapples.common.base.ApplicationException;
import cn.crabapples.minio.config.MinioConfigProperties;
import cn.crabapples.s3.config.S3ConfigProperties;
import cn.crabapples.s3.service.S3Service;
import cn.crabapples.system.sysFile.UPLOAD_TYPE;
import cn.crabapples.system.sysFile.entity.FileInfo;
import cn.crabapples.system.sysFile.service.FileInfoService;
import cn.crabapples.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component("upload_rustfs")
@Slf4j
public class UploadS3Strategy implements UploadFileStrategy {
    @Lazy
    @Resource
    private FileInfoService fileInfoService;

    private final S3Service service;

    public UploadS3Strategy(S3Service service) {
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
                RequestBody requestBody = RequestBody.fromInputStream(inputStream, part.getSize());
                String randomFileName = StringUtils.genRandomFileName(fileName);
                service.uploadFile(randomFileName, requestBody);
                S3ConfigProperties config = service.getConfig();
                String fullPath = config.getBucketName() + "/" + randomFileName;
                return new FileInfo()
                        .setVirtualPath(fullPath)
                        .setContentType(contentType)
                        .setFileSize(part.getSize())
                        .setOldName(fileName)
                        .setSaveType(UPLOAD_TYPE.RUST_FS.type);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        String url = fileInfoService.saveFileInfo(list).stream()
                .map(e -> "/api/file/download/" + UPLOAD_TYPE.RUST_FS + "/" + e.getVirtualPath())
                .collect(Collectors.joining(","));
        log.info("文件上传完成url:[{}]", url);
        return url;
    }

    @Override
    public void download(String bucket, String fileName, OutputStream outputStream) {
        ResponseInputStream<GetObjectResponse> responseInputStream = service.downloadFileAsStream(bucket, fileName);
        byte[] b = new byte[1024];
        try {
            for (int i = responseInputStream.read(b); i != -1; i = responseInputStream.read(b)) {
                outputStream.write(b, 0, i);
            }
            outputStream.close();
        } catch (IOException e) {
            log.error("下载文件失败", e);
            throw new ApplicationException("下载文件失败", e);
        }
    }

    @Override
    public String share(String bucket, String fileName) {
        return service.createTempDownloadUrl(bucket, fileName, 1L, ChronoUnit.HOURS).url().toString();
    }

    @Override
    public void remove(String fileName) {
        service.deleteFile(fileName);
    }
}
