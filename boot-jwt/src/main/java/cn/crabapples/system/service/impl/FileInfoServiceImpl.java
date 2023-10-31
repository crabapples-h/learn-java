package cn.crabapples.system.service.impl;

import cn.crabapples.common.utils.file.FileUtils;
import cn.crabapples.system.dao.FileInfoDAO;
import cn.crabapples.system.entity.FileInfo;
import cn.crabapples.system.service.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件功能实现类
 */
@Slf4j
@Service
public class FileInfoServiceImpl implements FileInfoService {
    @Value("${virtualPath}")
    private String virtualPath;
    @Value("${uploadPath}")
    private String uploadPath;

    private final FileInfoDAO fileInfoDAO;

    public FileInfoServiceImpl(FileInfoDAO fileInfoDAO) {
        this.fileInfoDAO = fileInfoDAO;
    }

    /**
     * 上传文件
     *
     * @return 返回上传的文件信息
     */
    @Override
    public FileInfo uploadFile(HttpServletRequest request) {
        MultipartFile multipartFile = getFile(request);
        FileUtils fileUtils = new FileUtils(uploadPath, virtualPath);
        FileInfo fileInfo = fileUtils.saveFile(multipartFile);
        return saveFileInfo(fileInfo);
    }

    private FileInfo saveFileInfo(FileInfo fileInfo) {
        fileInfoDAO.saveOrUpdate(fileInfo);
        return fileInfo;
    }

    @Override
    public FileInfo getById(String id) {
        return fileInfoDAO.findById(id);
    }
}
