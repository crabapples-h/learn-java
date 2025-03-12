package cn.crabapples.system.sysFile.dao;

import cn.crabapples.system.sysFile.dao.mybatis.mapper.FileInfoMapper;
import cn.crabapples.system.sysFile.entity.FileInfo;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class FileInfoDAO extends ServiceImpl<FileInfoMapper, FileInfo> {
}
