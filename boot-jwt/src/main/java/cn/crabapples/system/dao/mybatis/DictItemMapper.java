package cn.crabapples.system.dao.mybatis;

import cn.crabapples.system.entity.SysDictItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictItemMapper extends BaseMapper<SysDictItem> {
}
