package cn.crabapples.system.sysFile.strategy;

import cn.crabapples.common.utils.file.FileUtils;
import cn.crabapples.system.sysFile.entity.FileInfo;
import cn.crabapples.system.sysFile.service.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component("upload_local")
@Slf4j
public class UploadLocalStrategy implements UploadFileStrategy {
    @Value("${virtualPath}")
    private String virtualPath;
    @Value("${uploadPath}")
    private String uploadPath;
    private FileUtils fileUtils = null;

    @PostConstruct
    public void init() {
        fileUtils = new FileUtils(uploadPath, virtualPath);
    }

    @Lazy
    @Resource
    private FileInfoService fileInfoService;

    @Override
    public String upload(HttpServletRequest request) {
        return uploadFilePart(request);
    }


    /**
     * 上传文件-使用MultipartFile
     *
     * @param request 请求
     * @return 文件路径
     */
    private String uploadFileMultipart(HttpServletRequest request) {
        List<MultipartFile> files = fileInfoService.getFileMultipart(request);
        List<FileInfo> list = files.stream().map(part -> {
            String fileName = part.getOriginalFilename();
            String contentType = part.getContentType();
            try {
                InputStream inputStream = part.getInputStream();
                return fileUtils.saveFile(fileName, inputStream, contentType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        return fileInfoService.saveFileInfo(list).stream()
                .map(FileInfo::getVirtualPath)
                .collect(Collectors.joining(","));
    }

    /**
     * 上传文件-使用Part(只支持Servlet3)
     *
     * @param request 请求
     * @return 文件路径
     */
    private String uploadFilePart(HttpServletRequest request) {
        log.info("开始上传文件");
        Collection<Part> files = fileInfoService.getFileParts(request);
        List<FileInfo> list = files.stream().map(part -> {
            String fileName = part.getSubmittedFileName();
            String contentType = part.getContentType();
            try {
                InputStream inputStream = part.getInputStream();
                return fileUtils.saveFile(fileName, inputStream, contentType);
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

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
