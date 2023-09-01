package cn.crabapples.system.dao;

import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.system.dao.mybatis.FileInfoMapper;
import cn.crabapples.system.entity.FileInfo;
import org.springframework.stereotype.Component;

@Component
public class FileInfoDAO extends BaseDAO<FileInfo, String> {
    private final FileInfoMapper mapper;

    public FileInfoDAO(FileInfoMapper mapper) {
        this.mapper = mapper;
    }


    public int save(FileInfo fileInfo) {
        return mapper.insertOrUpdate(fileInfo);
    }

    public FileInfo findById(String id) {
        return mapper.selectOneById(id);
    }
}
