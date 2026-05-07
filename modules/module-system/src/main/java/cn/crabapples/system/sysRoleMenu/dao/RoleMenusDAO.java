package cn.crabapples.system.sysRoleMenu.dao;

import cn.crabapples.system.sysMenu.entity.SysMenu;
import cn.crabapples.system.sysRoleMenu.dao.mybatis.mapper.RoleMenusMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Component
public class RoleMenusDAO  {

    private final RoleMenusMapper mapper;

    public RoleMenusDAO(RoleMenusMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 根据获取角色菜单树
     *
     * @param id 角色id
     * @return 角色拥有的菜单树
     */
//    public SysRoleMenus getRoleMenusTree(String id) {
//        SysRoleMenus sysRoleMenus = baseMapper.getRoleMenus(id);
//        List<SysMenu> roleMenuList = sysRoleMenus.getSysMenus();
//        if (roleMenuList.isEmpty()) {
//            roleMenuList = Collections.emptyList();
//        }
//        List<String> menuIds = roleMenuList.stream()
//                .map(SysMenu::getId).collect(Collectors.toList());
//        List<SysMenu> allRootMenuTree = menusDAO.findMenusTree();
//        List<SysMenu> tree = super.filterRootMenusTree(menuIds, allRootMenuTree);
//
//
//        sysRoleMenus.setSysMenus(tree);
//        return sysRoleMenus;
//    }
    private List<SysMenu> convertToTree(List<SysMenu> list) {
        List<SysMenu> roots = new ArrayList<>();
        for (SysMenu node : list) {
            if (node.getPid() == null) {
                // 如果父节点ID为null，将此菜单视为根节点
                roots.add(node);
            } else {
                // 否则，寻找父节点，并将当前节点添加为其子节点
                addChildToParent(list, node);
            }
        }
        return roots;
    }


    private void addChildToParent(List<SysMenu> list, SysMenu menu) {
        for (SysMenu node : list) {
            System.err.println(menu);
            if (node.getId().equals(menu.getPid())) {
                // 找到了父节点，将当前节点添加为其子节点
                List<SysMenu> children = node.getChildren();
                if (Objects.isNull(children)) {
                    children = new ArrayList<>();
                }
                children.add(menu);
                node.setChildren(children);
                return;
            }
        }
    }

    // 获取角色拥有的菜单(包括按钮)
    public List<SysMenu> getRoleMenusList(String id) {
        return mapper.getRoleMenusList(id);
    }

    // 获取角色(多个)拥有的菜单(包括按钮)
    public List<SysMenu> getRoleMenusList(List<String> ids) {
        return mapper.getRoleListMenusList(ids);
    }

    public void saveRoleMenus(String id, List<String> menusList) {
        mapper.deleteRoleMenus(id);
        if (!menusList.isEmpty())
            menusList.forEach(e -> {
                mapper.saveRoleMenus(id, e);
            });
    }


    public void delByMenuId(String pid) {
        mapper.delByMenuId(pid);
    }
}
