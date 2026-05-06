package cn.crabapples.upload.dao;

import cn.crabapples.upload.dao.mybatis.mapper.FileInfoMapper;
import cn.crabapples.upload.entity.FileInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class FileInfoDAO extends ServiceImpl<FileInfoMapper, FileInfo> {
}
