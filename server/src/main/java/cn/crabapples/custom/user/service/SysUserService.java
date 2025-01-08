package cn.crabapples.custom.user.service;


import cn.crabapples.common.base.BaseService;
import cn.crabapples.custom.user.entity.SysUser;

import java.util.List;

public interface SysUserService extends BaseService<SysUser> {

    List<SysUser> queryList();
}
