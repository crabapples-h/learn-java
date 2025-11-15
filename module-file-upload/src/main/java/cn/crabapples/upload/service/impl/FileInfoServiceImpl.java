package cn.crabapples.upload.service.impl;

import cn.crabapples.common.base.ApplicationException;
import cn.crabapples.upload.strategy.UPLOAD_TYPE_ENUM;
import cn.crabapples.upload.dao.FileInfoDAO;
import cn.crabapples.upload.entity.FileInfo;
import cn.crabapples.upload.service.FileInfoService;
import cn.crabapples.upload.strategy.StrategyFactory;
import cn.crabapples.upload.strategy.UploadFileStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
    @PostConstruct
    public void init() {
        log.info("初始化文件上传服务");
    }
    @Resource
    private StrategyFactory strategyFactory;

    private final FileInfoDAO fileInfoDAO;

    public FileInfoServiceImpl(FileInfoDAO fileInfoDAO) {
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
    public String upload(HttpServletRequest request, UPLOAD_TYPE_ENUM type) {
        UploadFileStrategy strategy = strategyFactory.getBean(type);
        return strategy.upload(request);
    }


    @Override
    public void download(String bucket, String fileName, HttpServletResponse response, UPLOAD_TYPE_ENUM type) {
        try {
            UploadFileStrategy strategy = strategyFactory.getBean(type);
            strategy.download(bucket, fileName, response.getOutputStream());
        } catch (IOException e) {
            throw new ApplicationException("文件获取失败", e);
        }
    }

    @Override
    public String share( String bucket,String fileName, UPLOAD_TYPE_ENUM type) {
        UploadFileStrategy strategy = strategyFactory.getBean(type);
        return strategy.share(bucket,fileName);
    }

    @Override
    public void remove(String fileName, UPLOAD_TYPE_ENUM type) {
        UploadFileStrategy strategy = strategyFactory.getBean(type);
        strategy.remove(fileName);
    }
}
