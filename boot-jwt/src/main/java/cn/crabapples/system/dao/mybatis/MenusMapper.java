package cn.crabapples.system.dao.mybatis;

import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.entity.SysRoleMenus;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenusMapper extends BaseMapper<SysMenu> {

    SysRoleMenus getRoleMenus(@Param("roleId") String id);

    List<SysMenu> getUserMenus(@Param("userId") String id);

    List<SysMenu> findMenusTree();

    List<SysMenu> getRoleMenusList(@Param("roleId") String id);

    List<SysMenu> getRoleListMenusList(@Param("roleIds") List<String> ids);
}
