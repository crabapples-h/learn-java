package cn.crabapples.system.dao.mybatis;

import cn.crabapples.system.entity.FileInfo;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileInfoMapper extends BaseMapper<FileInfo> {
}
