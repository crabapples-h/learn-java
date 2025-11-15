package cn.crabapples.system.sysMenu.service;

import cn.crabapples.common.base.BaseService;
import cn.crabapples.common.dic.DIC;
import cn.crabapples.system.sysMenu.entity.SysMenu;
import cn.crabapples.system.sysMenu.form.MenusForm;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO 系统相关服务[菜单]
 *
 * @author Mr.He
 * 2021/4/25 0:34
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public interface SystemMenusService extends BaseService<SysMenu> {

    List<SysMenu> getChildList(String pid);

    List<SysMenu> getUserMenusTree();

    List<SysMenu> getMenusTreeList();

    Page<SysMenu> getMenuTreePage(Integer pageIndex, Integer pageSize, MenusForm form);

    boolean saveMenus(MenusForm form);

    boolean removeMenus(String id);

    Page<SysMenu> getMenuListPage(Integer pageIndex, Integer pageSize, MenusForm form);

    List<SysMenu> getUserMenusList();

    //    /**
//     * 将菜单树中标记为删除的去除
//     */
//    default List<SysMenu> filterMenusByDelFlag(List<SysMenu> sysMenus) {
//        return sysMenus.stream().filter(e -> {
//            List<SysMenu> children = filterMenusByDelFlag(e.getChildren());
//            e.setChildren(children);
//            return e.getDelFlag() == DIC.NOT_DEL;
//        }).collect(Collectors.toList());
//    }
//
//    /**
//     * 生成菜单树(思路为:从所有菜单中逐级递归过滤出角色没有权限的菜单，保留角色拥有的菜单)
//     * （角色表里存的菜单是字符串的形式，将字符串转换为数组然后分别把每个角色的菜单用递归的方法过滤出来）
//     * 1. 过滤掉被标记删除的菜单
//     * 2. 递归遍历过滤用户没有的菜单，最后的得到的是角色拥有的菜单树
//     * 3. 如果角色没有父级菜单但是有子菜单，则保留该父级菜单，但是不显示其他的子菜单
//     *
//     * @param userHasMenuIds 当前角色所拥有的菜单的ID
//     * @param allMenuTree    所有菜单树
//     * @return 角色拥有的菜单树
//     */
    default List<SysMenu> filterRootMenusTree(List<String> userHasMenuIds, List<SysMenu> allMenuTree) {
        return allMenuTree.stream()
                // 过了滤掉被删除的菜单
                .filter(e -> DIC.NOT_DEL == e.getDelFlag())
                .filter(e -> {
                    // children可能为null
                    List<SysMenu> children = e.getChildren();
                    e.setChildren(filterRootMenusTree(userHasMenuIds, children));
                    // 判断用户拥有的菜单中是否包含当前菜单
                    boolean hasMenu = userHasMenuIds.contains(e.getId());
                    // 判断用户拥有的菜单中是否包含当前菜单的子菜单,如果包含子菜单则需要将当前菜单一起返回
                    boolean hasChild = !e.getChildren().isEmpty();
                    return hasMenu || hasChild;
                }).sorted((a, b) -> a.getSort() - b.getSort())
                .collect(Collectors.toList());
    }
}
