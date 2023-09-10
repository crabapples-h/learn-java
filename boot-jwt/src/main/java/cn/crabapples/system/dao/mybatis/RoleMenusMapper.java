package cn.crabapples.system.dao.mybatis;

import cn.crabapples.system.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMenusMapper {

    List<SysMenu> getRoleMenusList(@Param("roleId") String id);

    List<SysMenu> getRoleListMenusList(@Param("roleIds") List<String> ids);

    void saveRoleMenus(@Param("roleId") String id, @Param("menuId") String menuId);

    void deleteRoleMenus(@Param("roleId") String id);

    void delByMenuId( @Param("menuId") String menuId);
}
