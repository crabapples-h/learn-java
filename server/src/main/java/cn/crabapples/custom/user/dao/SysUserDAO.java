package cn.crabapples.custom.user.dao;

import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.custom.user.entity.SysUser;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SysUserDAO extends BaseDAO {
    private final SysUserMapper mapping;

    public SysUserDAO(SysUserMapper mapping) {
        this.mapping = mapping;
    }

    public List<SysUser> queryList( ) {
        return mapping.queryList();
    }
}
