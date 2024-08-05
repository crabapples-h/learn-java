package cn.crabapples.system.sysUserRole.dao;

import cn.crabapples.system.sysUserRole.dao.mybatis.mapper.UserRolesMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRolesDAO {
    private final UserRolesMapper mapper;

    public UserRolesDAO(UserRolesMapper mapper) {
        this.mapper = mapper;
    }

    public void saveUserRoles(String userId, List<String> roleList) {
        mapper.deleteUserRoles(userId);
        if (!roleList.isEmpty())
            roleList.forEach(e -> {
                mapper.saveUserRoles(userId, e);
            });
    }

}
