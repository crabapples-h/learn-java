package cn.crabapples.system.sysRoleMenu.service;

import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.sysMenu.entity.SysMenu;

import java.util.List;


public interface SystemRoleMenusService extends BaseService {

    List<SysMenu> getRoleMenusList(String id);

    List<SysMenu> getRoleMenusList(List<String> ids);

    void saveRoleMenus(String id, List<String> menusList);

    void delByMenuId(String pid);

    List<SysMenu> getRoleMenusTree(String id);
}
