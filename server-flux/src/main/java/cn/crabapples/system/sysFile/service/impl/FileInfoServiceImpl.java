package cn.crabapples.system.sysFile.service.impl;

import cn.crabapples.common.utils.file.FileUtils;
import cn.crabapples.system.sysFile.dao.mybatis.mapper.FileInfoMapper;
import cn.crabapples.system.sysFile.entity.FileInfo;
import cn.crabapples.system.sysFile.service.FileInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {
    @Value("${virtualPath}")
    private String virtualPath;
    @Value("${uploadPath}")
    private String uploadPath;

//    private final FileInfoDAO fileInfoDAO;
//
//    public FileInfoServiceImpl(FileInfoDAO fileInfoDAO) {
//        this.fileInfoDAO = fileInfoDAO;
//    }

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
        saveOrUpdate(fileInfo);
        return fileInfo;
    }
}
