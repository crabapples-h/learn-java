package cn.crabapples.system.sysUserRole.dao;

import cn.crabapples.system.sysUserRole.dao.mybatis.mapper.UserRolesMapper;
import cn.hutool.core.collection.CollectionUtil;
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
        if (!CollectionUtil.isEmpty(roleList)) {
            roleList.forEach(e -> mapper.saveUserRoles(userId, e));
        }
    }

}
