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

    List<SysMenu> findButtonsByIds(@Param("ids") List<String> ids);

    List<SysMenu> getUserMenus(@Param("userId") String id);

    List<SysMenu> findMenusTree();

}
