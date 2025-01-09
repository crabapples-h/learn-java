package cn.crabapples.system.sysTenant.dao.mybatis.mapper;

import cn.crabapples.system.sysTenant.entity.SysTenant;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TenantMapper extends BaseMapper<SysTenant> {
}
