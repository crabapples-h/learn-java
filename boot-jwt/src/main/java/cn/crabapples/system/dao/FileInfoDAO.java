package cn.crabapples.system.dao;

import cn.crabapples.common.BaseDAO;
import cn.crabapples.system.dao.jpa.FileInfoRepository;
import cn.crabapples.system.entity.FileInfo;
import org.springframework.stereotype.Component;

@Component
public class FileInfoDAO extends BaseDAO<FileInfo, String> {
    private final FileInfoRepository repository;

    public FileInfoDAO(FileInfoRepository repository) {
        this.repository = repository;
    }

    public FileInfo save(FileInfo fileInfo) {
        return repository.saveAndFlush(fileInfo);
    }

    public FileInfo findById(String id) {
        return repository.findById(id).orElse(new FileInfo());
    }
}
