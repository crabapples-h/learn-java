package cn.crabapples.system.service.impl;

import cn.crabapples.system.dao.RoleMenusDAO;
import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.service.SystemRoleMenusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class SystemRolesMenusServiceImpl implements SystemRoleMenusService {
    private final RoleMenusDAO roleMenusDAO;


    public SystemRolesMenusServiceImpl(RoleMenusDAO roleMenusDAO) {
        this.roleMenusDAO = roleMenusDAO;
    }

    /**
     * 根据获取角色菜单列表
     *
     * @param roleId 角色id
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> getRoleMenusList(String roleId) {
        return listToTree(roleMenusDAO.getRoleMenusList(roleId));
    }

    private List<SysMenu> listToTree(List<SysMenu> list) {
        for (SysMenu sysMenu : list) {

        }
        return list;
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
