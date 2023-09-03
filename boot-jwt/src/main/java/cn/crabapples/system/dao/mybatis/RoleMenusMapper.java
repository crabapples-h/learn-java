package cn.crabapples.system.dao.mybatis;

import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.entity.SysRoleMenus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMenusMapper extends BaseMapper<SysRoleMenus> {

    SysRoleMenus getRoleMenus(@Param("roleId") String id);

    List<SysMenu> getRoleMenusList(@Param("roleId") String id);

    List<SysMenu> getRoleListMenusList(@Param("roleIds") List<String> ids);

    void saveRoleMenus(@Param("roleId")String id, @Param("menuIds")List<String> menusList);

    void deleteRoleMenus(@Param("roleId")String id);
}
