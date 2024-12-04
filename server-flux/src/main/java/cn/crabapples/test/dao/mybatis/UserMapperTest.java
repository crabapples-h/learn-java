package cn.crabapples.test.dao.mybatis;

import cn.crabapples.test.entity.SysUser;
import cn.crabapples.test.entity.SysUserExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapperTest  {
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
