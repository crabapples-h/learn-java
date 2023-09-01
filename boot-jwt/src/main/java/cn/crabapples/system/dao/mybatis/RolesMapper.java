package cn.crabapples.system.dao.mybatis;

import cn.crabapples.system.dto.SysRolesDTO;
import cn.crabapples.system.entity.SysMenus;
import cn.crabapples.system.entity.SysRoleMenus;
import cn.crabapples.system.entity.SysRoles;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RolesMapper extends BaseMapper<SysRoles> {
    List<SysRoleMenus> getRoleMenus(String id);

    List<SysMenus> findButtonsByIds(@Param("ids") List<String> ids);

    List<SysRolesDTO> getUserRoles(@Param("user_id")String id);
}
