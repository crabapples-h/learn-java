package cn.crabapples.system.sysFile.service;

import cn.crabapples.common.utils.file.FileUtils;
import cn.crabapples.system.sysFile.entity.FileInfo;
import com.mybatisflex.core.service.IService;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 文件功能抽象接口
 */
public interface FileInfoService {
    default FileInfo getFilePath(HttpServletRequest request, String uploadPath, String virtualPath) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("file");
        FileUtils fileUtils = new FileUtils(uploadPath, virtualPath);
        return fileUtils.saveFile(multipartFile);
    }

    default MultipartFile getFile(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        return multipartRequest.getFile("file");
    }

    FileInfo uploadFile(HttpServletRequest request);

    String uploadFile2Oss(HttpServletRequest request);

    void fileDownload(String url, HttpServletResponse response) throws IOException;
}
