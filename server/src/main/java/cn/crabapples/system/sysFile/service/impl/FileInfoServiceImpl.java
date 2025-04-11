package cn.crabapples.system.sysFile.service.impl;

import cn.crabapples.common.base.ApplicationException;
import cn.crabapples.common.minio.MinioUtils;
import cn.crabapples.system.sysFile.UPLOAD_TYPE;
import cn.crabapples.system.sysFile.dao.FileInfoDAO;
import cn.crabapples.system.sysFile.entity.FileInfo;
import cn.crabapples.system.sysFile.service.FileInfoService;
import cn.crabapples.system.sysFile.strategy.StrategyFactory;
import cn.crabapples.system.sysFile.strategy.UploadFileStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 文件功能实现类
 */
@Slf4j
@Service
public class FileInfoServiceImpl implements FileInfoService {
    @Resource
    private StrategyFactory strategyFactory;

    private final FileInfoDAO fileInfoDAO;

    public FileInfoServiceImpl(FileInfoDAO fileInfoDAO, MinioUtils minioUtils) {
        this.fileInfoDAO = fileInfoDAO;
    }

    @Override
    public List<FileInfo> saveFileInfo(List<FileInfo> fileInfo) {
        fileInfoDAO.saveOrUpdateBatch(fileInfo);
        return fileInfo;
    }

    @Override
    public FileInfo saveFileInfo(FileInfo fileInfo) {
        fileInfoDAO.saveOrUpdate(fileInfo);
        return fileInfo;
    }

    @Override
    public String upload(HttpServletRequest request, UPLOAD_TYPE type) {
        UploadFileStrategy strategy = strategyFactory.getBean(type);
        return strategy.upload(request);
    }


    @Override
    public void download(String fileName, HttpServletResponse response, UPLOAD_TYPE type) {
        try {
            UploadFileStrategy strategy = strategyFactory.getBean(type);
            strategy.download(fileName, response.getOutputStream());
        } catch (IOException e) {
            throw new ApplicationException("文件获取失败", e);
        }
    }

    @Override
    public String share(String fileName, UPLOAD_TYPE type) {
        UploadFileStrategy strategy = strategyFactory.getBean(type);
        return strategy.share(fileName);
    }

    @Override
    public void remove(String fileName, UPLOAD_TYPE type) {
        UploadFileStrategy strategy = strategyFactory.getBean(type);
         strategy.remove(fileName);
    }
}
