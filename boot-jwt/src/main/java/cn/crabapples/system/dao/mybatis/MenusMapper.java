package cn.crabapples.system.dao.mybatis;

import cn.crabapples.system.entity.SysMenus;
import cn.crabapples.system.entity.SysRoleMenus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenusMapper {
    List<SysRoleMenus> getRoleMenus(String id);

    List<SysMenus> findButtonsByIds(@Param("ids") List<String> ids);
}
