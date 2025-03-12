package cn.crabapples.system.sysFile.service.impl;

import cn.crabapples.common.base.ApplicationException;
import cn.crabapples.common.minio.MinioConfigure;
import cn.crabapples.common.minio.MinioUtils;
import cn.crabapples.common.utils.file.FileUtils;
import cn.crabapples.system.sysFile.dao.FileInfoDAO;
import cn.crabapples.system.sysFile.dao.mybatis.mapper.FileInfoMapper;
import cn.crabapples.system.sysFile.entity.FileInfo;
import cn.crabapples.system.sysFile.service.FileInfoService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Upload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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
    private final MinioUtils minioUtils;

    public FileInfoServiceImpl(FileInfoDAO fileInfoDAO,  MinioUtils minioUtils) {
        this.fileInfoDAO = fileInfoDAO;
        this.minioUtils = minioUtils;
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
    public String uploadFile2Oss(HttpServletRequest request) {
        log.info("开始上传文件");
        String url = minioUtils.upload(getFile(request));
        log.info("文件上传完成url:[{}]", url);
        return url;
    }
}
