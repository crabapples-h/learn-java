package cn.crabapples.custom.user;

import cn.crabapples.common.base.BaseDAO;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SysUserDAO extends BaseDAO {
    private final SysUserMapper mapping;

    public SysUserDAO(ClassRoomMapper mapping) {
        this.mapping = mapping;
    }

    public List<SysUser> queryList( ) {
        return mapping.queryList();
    }
}
