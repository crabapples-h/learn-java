package cn.crabapples.system.sysFile.strategy;

import cn.crabapples.common.minio.MinioUtils;
import cn.crabapples.system.sysFile.UploadTypeEnum;
import cn.crabapples.system.sysFile.entity.FileInfo;
import cn.crabapples.system.sysFile.service.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component("upload_minio")
@Slf4j
public class UploadMinioStrategy implements UploadFileStrategy {
    @Value("${virtualPath}")
    private String virtualPath;
    @Value("${uploadPath}")
    private String uploadPath;

    @Lazy
    @Resource
    private FileInfoService fileInfoService;

    private final MinioUtils minioUtils;

    public UploadMinioStrategy(MinioUtils minioUtils) {
        this.minioUtils = minioUtils;
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
                String fullPath = minioUtils.upload(null, inputStream, fileName);
                String urlPrefix = fullPath.substring(0, fullPath.indexOf("/"));
                String bucketName = fullPath.substring(urlPrefix.length() + 1, fullPath.indexOf("/", urlPrefix.length() + 1));
                String uploadFileName = fullPath.substring(urlPrefix.length() + 1 + bucketName.length() + 1);
                return new FileInfo()
                        .setVirtualPath("/api/file/download?fileName=" + uploadFileName)
                        .setContentType(contentType)
                        .setFileSize(part.getSize())
                        .setOldName(fileName)
                        .setUploadPath(fullPath)
                        .setSaveType(UploadTypeEnum.MINIO.type);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        String url = fileInfoService.saveFileInfo(list).stream()
                .map(FileInfo::getVirtualPath)
                .collect(Collectors.joining(","));
        log.info("文件上传完成url:[{}]", url);
        return url;
    }

    public static void main(String[] args) {
//        String imgPath = "/crabapples/ddd/test.txt";
//        String[] split = imgPath.split("/");
//
//        String prefix = imgPath.substring(0, imgPath.indexOf("/"));
//        String bucketName = imgPath.substring(prefix.length() + 1, imgPath.indexOf("/", prefix.length() + 1));
//        String fileName = imgPath.substring(prefix.length() + 1 + bucketName.length() + 1);
//
//        System.err.println(prefix);
//        System.err.println(bucketName);
//        System.err.println(fileName);
    }
}
