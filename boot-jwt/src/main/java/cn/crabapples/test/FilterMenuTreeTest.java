package cn.crabapples.test;

import cn.crabapples.common.dic.DIC;
import cn.crabapples.system.entity.SysMenu;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FilterMenuTreeTest {
//    public static void main(String[] args) {
//        List<SysMenu> allRootMenuTree = (List<SysMenu>) Utils.readObj("allRootMenuTree");
//        List<String> userMenuIds = new ArrayList<>();
//        userMenuIds.add("4bfb47b0-1235-40da-ab56-8fb5863957fa");
//        userMenuIds.add("4bfb47b0-1235-40da-ab56-8fb5863957fa");
//
//
//
//
//        for (String id : userMenuIds) {
//            List<SysMenu> menusTree = filterRootMenusTree(id, allRootMenuTree);
//            System.err.println(menusTree);
//        }
//    }

    // 系统管理->用户管理->锁定用户
    public static List<SysMenu> filterRootMenusTree(String id, List<SysMenu> allMenuTree) {
        List<SysMenu> exist = new ArrayList<>();
        for (SysMenu menu : allMenuTree) {
            String menuName = menu.getName();
            if (DIC.IS_DEL == menu.getDelFlag()) {
                log.warn("菜单:[{}]已被标记为删除状态", menuName);
                continue;
            }
            if (menu.getId().equals(id)) {
                log.info("菜单:[{}]拥有", menuName);
                exist.add(menu);
            }
            List<SysMenu> children = menu.getChildren();
            if (!children.isEmpty()) {
                return filterRootMenusTree(id, children);
            }
//                System.err.println(children);
        }
        return exist;
    }


    static List<SysMenu> filterRootMenusTree1(List<String> userMenuList, List<SysMenu> allMenuTree) {
        return allMenuTree.stream().filter(e -> {
            System.err.println(e);
            // 判断当前菜单是否被标记删除
            if (DIC.IS_DEL == e.getDelFlag()) {
                return false;
            }
            // 判断当前菜单是否是按钮
//            if (DIC.MENUS_TYPE_BUTTON == e.getMenusType()) {
//                return false;
//            }
            // children可能为null
            List<SysMenu> children = e.getChildren();
            e.setChildren(filterRootMenusTree1(userMenuList, children));
            // 判断用户拥有的菜单中是否包含当前菜单
            boolean exist = userMenuList.contains(e.getId());
            // 判断用户拥有的菜单中是否包含当前菜单的子菜单
            boolean sizeZero = children.size() > 0;
            return exist || sizeZero;
        }).collect(Collectors.toList());
    }

}
