package cn.crabapples.system.dao;

import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.system.dao.jpa.FileInfoRepository;
import cn.crabapples.system.entity.FileInfo;
import org.springframework.stereotype.Component;

@Component
public class FileInfoDAO extends BaseDAO {
    private final FileInfoRepository fileInfoRepository;

    public FileInfoDAO(FileInfoRepository fileInfoRepository) {
        this.fileInfoRepository = fileInfoRepository;
    }

    public FileInfo save(FileInfo fileInfo) {
        return fileInfoRepository.saveAndFlush(fileInfo);
    }

    public FileInfo findById(String id) {
        return fileInfoRepository.findById(id).orElse(new FileInfo());
    }
}
