package cn.crabapples.system.sysUserRole.dao.mybatis.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRolesMapper {

    @Delete("delete from sys_user_roles where user_id = #{userId}")
    void deleteUserRoles(@Param("userId") String userId);

    @Insert("insert into sys_user_roles values (#{userId}, #{roleId})")
    void saveUserRoles(@Param("userId") String userId, String roleId);
}
