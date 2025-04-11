package cn.crabapples.system.sysFile.service.impl;

import cn.crabapples.common.minio.MinioUtils;
import cn.crabapples.system.sysFile.UploadTypeEnum;
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

    /**
     * 上传文件
     *
     * @return 返回上传的文件信息
     */
    @Override
    public String uploadFile(HttpServletRequest request, UploadTypeEnum func) {
        UploadFileStrategy upload = strategyFactory.getBean(func);
        return upload.upload(request);
    }


    @Override
    public void fileDownload(String fileName, HttpServletResponse response) throws IOException {
//        minioUtils.download(fileName, response.getOutputStream());
    }

    @Override
    public String fileShare(String fileName) {
//        return minioUtils.share(fileName);
        return "";
    }
}
