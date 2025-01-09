package cn.crabapples.system.sysDict.dao.mybatis.mapper;

import cn.crabapples.system.sysDict.entity.SysDict;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictMapper extends BaseMapper<SysDict> {
}
