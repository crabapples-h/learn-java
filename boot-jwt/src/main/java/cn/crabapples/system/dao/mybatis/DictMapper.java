package cn.crabapples.system.dao.mybatis;

import cn.crabapples.system.entity.SysDict;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictMapper extends BaseMapper<SysDict> {
}