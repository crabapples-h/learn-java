package cn.crabapples.system.service.impl;

import cn.crabapples.system.dao.MenusDAO;
import cn.crabapples.system.dao.RoleMenusDAO;
import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.entity.SysRoleMenus;
import cn.crabapples.system.service.SystemRoleMenusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class SystemRolesMenusServiceImpl implements SystemRoleMenusService {
    private final RoleMenusDAO roleMenusDAO;
    private final MenusDAO menusDAO;


    public SystemRolesMenusServiceImpl(RoleMenusDAO roleMenusDAO, MenusDAO menusDAO) {
        this.roleMenusDAO = roleMenusDAO;
        this.menusDAO = menusDAO;
    }

    /**
     * 根据获取角色菜单树
     *
     * @param id 角色id
     * @return 菜单树
     */
    @Override
    public SysRoleMenus getRoleMenusTree(String id) {
        SysRoleMenus sysRoleMenus = roleMenusDAO.getRoleMenus(id);
        List<SysMenu> roleMenuList = sysRoleMenus.getSysMenus();
        if (roleMenuList.isEmpty()) {
            roleMenuList = Collections.emptyList();
        }
        List<String> menuIds = roleMenuList.stream()
                .map(SysMenu::getId).collect(Collectors.toList());
        List<SysMenu> allRootMenuTree = menusDAO.findMenusTree();
        List<SysMenu> tree = filterRootMenusTree(menuIds, allRootMenuTree);
        sysRoleMenus.setSysMenus(tree);
        return sysRoleMenus;
    }

    @Override
    public List<SysMenu> getRoleMenusList(String id) {
        return roleMenusDAO.getRoleMenusList(id);
    }

    @Override
    public List<SysMenu> getRoleMenusList(List<String> ids) {
        return roleMenusDAO.getRoleMenusList(ids);
    }

    @Override
    public void saveRoleMenus(String id, List<String> menusList) {
        roleMenusDAO.saveRoleMenus(id, menusList);
    }
}
