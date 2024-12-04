package cn.crabapples.system.sysFile.dao;

import cn.crabapples.system.sysFile.dao.mybatis.mapper.FileInfoMapper;
import cn.crabapples.system.sysFile.entity.FileInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class FileInfoDAO extends ServiceImpl<FileInfoMapper, FileInfo> {
//    private final FileInfoRepository repository;
//
//    public FileInfoDAO(FileInfoRepository repository) {
//        this.repository = repository;
//    }
//
//    public FileInfo save(FileInfo fileInfo) {
//        return repository.saveAndFlush(fileInfo);
//    }
//
//    public FileInfo findById(String id) {
//        return repository.findById(id).orElse(new FileInfo());
//    }
}
