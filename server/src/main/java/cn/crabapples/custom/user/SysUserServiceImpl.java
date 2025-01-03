package cn.crabapples.custom.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    private final SysUserDAO dao;

    public SysUserServiceImpl(SysUserDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<SysUser> queryList( ) {
        return dao.queryList();
    }
}
