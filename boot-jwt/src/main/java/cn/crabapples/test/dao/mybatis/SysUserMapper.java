package cn.crabapples.test.dao.mybatis;

import cn.crabapples.test.entity.SysUser;
import cn.crabapples.test.entity.SysUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUser row);

    int insertSelective(SysUser row);

    List<SysUser> selectByExampleWithBLOBs(SysUserExample example);

    List<SysUser> selectByExample(SysUserExample example);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser row);

    int updateByPrimaryKeyWithBLOBs(SysUser row);

    int updateByPrimaryKey(SysUser row);
}
