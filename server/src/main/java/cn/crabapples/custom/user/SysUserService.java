package cn.crabapples.custom.user;


import cn.crabapples.common.base.BaseService;

import java.util.List;

public interface SysUserService extends BaseService<SysUser> {

    List<SysUser> queryList();
}
