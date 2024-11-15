package cn.crabapples.system.sysFile.service;

import cn.crabapples.common.utils.file.FileUtils;
import cn.crabapples.system.sysFile.entity.FileInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件功能抽象接口
 */
public interface FileInfoService extends IService<FileInfo> {
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
}
