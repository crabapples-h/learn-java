package cn.crabapples.custom.user.service.impl;

import org.springframework.stereotype.Service;
import cn.crabapples.custom.user.entity.SysUser;
import cn.crabapples.custom.user.dao.SysUserDAO;


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
