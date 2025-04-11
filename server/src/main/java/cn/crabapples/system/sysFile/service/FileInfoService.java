package cn.crabapples.system.sysFile.service;

import cn.crabapples.common.base.ApplicationException;
import cn.crabapples.system.sysFile.UPLOAD_TYPE;
import cn.crabapples.system.sysFile.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.Collection;
import java.util.List;

/**
 * 文件功能抽象接口
 */
public interface FileInfoService {
    default List<MultipartFile> getFileMultipart(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        return multipartRequest.getFiles("file");
    }

    // 只支持servlet3
    default Collection<Part> getFileParts(HttpServletRequest request) {
        try {
            return request.getParts();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }


    List<FileInfo> saveFileInfo(List<FileInfo> fileInfo);

    FileInfo saveFileInfo(FileInfo fileInfo);

    String upload(HttpServletRequest request, UPLOAD_TYPE func);

    void download(String fileName, HttpServletResponse response, UPLOAD_TYPE type);

    String share(String fileName, UPLOAD_TYPE type);

    void remove(String fileName, UPLOAD_TYPE type);
}
