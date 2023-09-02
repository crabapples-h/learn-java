package cn.crabapples.system.dao.mybatis;

import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.entity.SysRole;
import cn.crabapples.system.entity.SysRoleMenus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RolesMapper extends BaseMapper<SysRole> {
    List<SysRoleMenus> getRoleMenus(String id);

    List<SysMenu> findButtonsByIds(@Param("ids") List<String> ids);

    List<SysRole> getUserRoles(@Param("userId")String id);
}
