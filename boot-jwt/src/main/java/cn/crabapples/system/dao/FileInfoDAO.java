package cn.crabapples.system.dao;

import cn.crabapples.system.dao.mybatis.FileInfoMapper;
import cn.crabapples.system.entity.FileInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class FileInfoDAO extends ServiceImpl<FileInfoMapper, FileInfo> {

    public FileInfo findById(String id) {
        return getById(id);
    }
}
