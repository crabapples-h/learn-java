package cn.crabapples.system.sysRoleMenu.service.impl;

import cn.crabapples.system.sysMenu.dao.SystemMenusDAO;
import cn.crabapples.system.sysMenu.entity.SysMenu;
import cn.crabapples.system.sysRoleMenu.dao.RoleMenusDAO;
import cn.crabapples.system.sysRoleMenu.service.SystemRoleMenusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class SystemRolesMenusServiceImpl implements SystemRoleMenusService {
    private final RoleMenusDAO roleMenusDAO;
    private final SystemMenusDAO menusDAO;


    public SystemRolesMenusServiceImpl(RoleMenusDAO roleMenusDAO, SystemMenusDAO menusDAO) {
        this.roleMenusDAO = roleMenusDAO;
        this.menusDAO = menusDAO;
    }

    /**
     * 根据获取角色菜单列表
     *
     * @param roleId 角色id
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> getRoleMenusList(String roleId) {
        return roleMenusDAO.getRoleMenusList(roleId);
    }

    public List<SysMenu> getRoleMenusTree(String roleId) {
        // 查找所有菜单树
        List<SysMenu> menusTree = menusDAO.findMenusTreeList();
        // 获取当前角色拥有的菜单
        List<SysMenu> hasMenus = roleMenusDAO.getRoleMenusList(roleId);
        List<String> hasMenuIds = hasMenus.stream().map(SysMenu::getId).collect(Collectors.toList());
        // 过滤掉没有权限的菜单
        return (List<SysMenu>) filterRootMenusTree(hasMenuIds, menusTree);
    }

    private List<SysMenu> tree2List(List<SysMenu> source, List<SysMenu> target) {
        for (SysMenu sysMenu : source) {
            target.add(sysMenu);
            tree2List(sysMenu.getChildren(), target);
        }
        return target;
    }

    @Override
    public List<SysMenu> getRoleMenusList(List<String> ids) {
        return roleMenusDAO.getRoleMenusList(ids);
    }

    @Override
    public void saveRoleMenus(String id, List<String> menusList) {
        roleMenusDAO.saveRoleMenus(id, menusList);
    }

    @Override
    public void delByMenuId(String pid) {
        roleMenusDAO.delByMenuId(pid);
    }
}
