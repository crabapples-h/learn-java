package cn.crabapples.system.service;

import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.entity.SysRoleMenus;

import java.util.List;


public interface SystemRoleMenusService extends BaseService {

    SysRoleMenus getRoleMenusTree(String id);

    List<SysMenu> getRoleMenusList(String id);

    List<SysMenu> getRoleMenusList(List<String> ids);

    void saveRoleMenus(String id, List<String> menusList);
}
