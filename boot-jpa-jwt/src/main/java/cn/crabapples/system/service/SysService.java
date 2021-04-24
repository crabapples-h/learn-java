package cn.crabapples.system.service;

import cn.crabapples.common.DIC;
import cn.crabapples.common.PageDTO;
import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.dto.SysRolesDTO;
import cn.crabapples.system.entity.SysMenus;
import cn.crabapples.system.entity.SysRoles;
import cn.crabapples.system.form.MenusForm;
import cn.crabapples.system.form.RolesForm;
import cn.crabapples.system.form.UserForm;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO 系统相关服务
 *
 * @author Mr.He
 * 2020/1/28 23:22
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public interface SysService extends BaseService {
    /**
     * 生成菜单树(思路为:从所有菜单中逐级递归过滤出角色没有权限的菜单，保留角色拥有的菜单)
     * （角色表里存的菜单是字符串的形式，将字符串转换为数组然后分别把每个角色的菜单用递归的方法过滤出来）
     * 递归遍历所有菜单，判断菜单ID是否为角色所拥有，从最后一级菜单开始逐级返回子菜单中角色所拥有的菜单
     * 如果菜单被标记删除则直接将该菜单及其子菜单移除
     *
     * @param menusIds 当前角色所拥有的菜单的ID
     * @param allMenus 所有根菜单
     * @return 角色拥有的菜单树
     */
    default List<SysMenus> filterRootMenusTree(List<String> menusIds, List<SysMenus> allMenus) {
        return allMenus.stream().filter(e -> {
            /*
             * 判断当前菜单是否被标记删除
             */
            if (DIC.IS_DEL == e.getDelFlag()) {
                return false;
            }
            List<SysMenus> children = filterRootMenusTree(menusIds, e.getChildren());
            e.setChildren(children);
            /*
             * 判断用户拥有的菜单中是否包含当前菜单
             */
            boolean exist = menusIds.contains(e.getId());
            /*
             * 判断用户拥有的菜单中是否包含当前菜单的子菜单
             */
            boolean sizeZero = children.size() > 0;

            return exist || sizeZero;
        }).collect(Collectors.toList());
    }

    String loginCheck(UserForm form);


    List<SysMenus> getMenusListPage(HttpServletRequest request, PageDTO page);

    List<SysMenus> getMenusList(HttpServletRequest request);

    SysMenus saveMenus(MenusForm form);

    List<SysMenus> getUserMenus(HttpServletRequest request);

    List<String> getUserPermissions(HttpServletRequest request);

    SysMenus removeMenus(String id);

    List<SysRolesDTO> getUserRolesList(HttpServletRequest request);

    List<SysRoles> getRolesListPage(HttpServletRequest request, PageDTO page);

    List<SysRoles> getRolesList(HttpServletRequest request);


    SysRoles saveRoles(RolesForm form);

    SysRoles removeRoles(String id);

}
