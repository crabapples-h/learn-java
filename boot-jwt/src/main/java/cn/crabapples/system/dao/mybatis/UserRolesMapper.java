package cn.crabapples.system.dao.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRolesMapper {

    void deleteUserRoles(@Param("userId") String userId);

    void saveUserRoles(@Param("userId") String userId, String roleId);
}
