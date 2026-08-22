package cn.crabapples.system.sysMenu.service.impl;

import cn.crabapples.system.sysMenu.dao.SystemMenusDAO;
import cn.crabapples.system.sysMenu.entity.SysMenu;
import cn.crabapples.system.sysMenu.form.MenusForm;
import cn.crabapples.system.sysMenu.service.SystemMenusService;
import cn.crabapples.system.sysRoleMenu.service.SystemRoleMenusService;
import cn.crabapples.system.sysUser.entity.SysUser;
import cn.crabapples.system.sysUser.service.SystemUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * TODO 系统相关服务实现类[菜单]
 *
 * @author Mr.He
 * 2020/1/28 23:23
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Service
@Slf4j
//@CacheConfig(cacheNames = "user:")
public class SystemMenusServiceImpl implements SystemMenusService {
    private final HttpServletRequest request;
    private final SystemUserService userService;
    private final SystemRoleMenusService roleMenusService;
    private final SystemMenusDAO systemMenusDAO;

    public SystemMenusServiceImpl(HttpServletRequest request, SystemMenusDAO systemMenusDAO,
                                  SystemUserService userService, SystemRoleMenusService roleMenusService) {
        this.request = request;
        this.systemMenusDAO = systemMenusDAO;
        this.userService = userService;
        this.roleMenusService = roleMenusService;
    }

    @Override
    public List<SysMenu> getChildList(String pid) {
        return systemMenusDAO.getChildList(pid);
    }

    /**
     * 获取当前用户拥有的菜单树（修复 P0-3：全量查一次 + Java 组装树，避免 N+1）
     */
    @Override
    public List<SysMenu> getUserMenusTree() {
        log.debug("获取用户拥有的所有菜单");
        SysUser user = userService.getUserInfo();
        List<SysMenu> userMenus = systemMenusDAO.getUserMenus(user.getId());
        List<String> userMenuIds = userMenus.stream()
                .map(SysMenu::getId).collect(Collectors.toList());
        // 一次全量查询所有菜单，在 Java 层组装树（避免 MyBatis collection 递归的 N+1）
        List<SysMenu> allMenus = systemMenusDAO.findAllMenusFlat();
        List<SysMenu> list = buildMenuTree(allMenus, userMenuIds);
        log.debug("用户拥有的所有菜单[{}]", list);
        return list;
    }


    @Override
    public boolean removeMenus(String id) {
        // 级联删除该菜单在角色-菜单关联表中的记录（修复 P0-2）
        roleMenusService.delByMenuId(id);
        return systemMenusDAO.remove(id);
    }

    @Override
    public boolean saveMenus(MenusForm form) {
        if (StringUtils.isNotBlank(form.getPid())) {
            // 当添加子菜单时，将其父级菜单从已拥有的权限中移除，避免角色直接拥有新添加的菜单的权限
            roleMenusService.delByMenuId(form.getPid());
        }
        return systemMenusDAO.saveOrUpdate(form.toEntity());
    }

    @Override
    public List<SysMenu> getMenusTreeList() {
        return buildMenuTree(systemMenusDAO.findAllMenusFlat(), Collections.emptyList());
    }

    /**
     * 在 Java 层按 pid 组装树结构（O(n) 一次遍历），替代 MyBatis collection 递归的 N+1
     */
    private List<SysMenu> buildMenuTree(List<SysMenu> allMenus, List<String> userMenuIds) {
        Map<String, SysMenu> menuMap = new HashMap<>();
        for (SysMenu m : allMenus) {
            // children 实体字段默认是不可变 EMPTY_LIST，组装树前先初始化为可变集合
            m.setChildren(new ArrayList<>());
            menuMap.put(m.getId(), m);
        }
        List<SysMenu> roots = new ArrayList<>();
        for (SysMenu m : allMenus) {
            if (m.getPid() == null) {
                roots.add(m);
            } else {
                SysMenu parent = menuMap.get(m.getPid());
                if (parent != null) {
                    parent.getChildren().add(m);
                } else {
                    roots.add(m);
                }
            }
        }
        if (userMenuIds.isEmpty()) {
            return roots;
        }
        return filterTree(roots, userMenuIds);
    }

    private List<SysMenu> filterTree(List<SysMenu> nodes, List<String> userMenuIds) {
        List<SysMenu> result = new ArrayList<>();
        for (SysMenu node : nodes) {
            List<SysMenu> children = filterTree(node.getChildren(), userMenuIds);
            node.setChildren(children);
            boolean hasMenu = userMenuIds.contains(node.getId());
            boolean hasChild = !children.isEmpty();
            if (hasMenu || hasChild) {
                result.add(node);
            }
        }
        return result;
    }

    @Override
    public Page<SysMenu> getMenuTreePage(Integer pageIndex, Integer pageSize, MenusForm form) {
        return systemMenusDAO.getMenuTreePage(Page.of(pageIndex, pageSize));
    }

    @Override
    public Page<SysMenu> getMenuListPage(Integer pageIndex, Integer pageSize, MenusForm form) {
        return systemMenusDAO.getMenuListPage(Page.of(pageIndex, pageSize),form);
    }

    @Override
    public List<SysMenu> getUserMenusList() {
        log.debug("获取用户拥有的所有菜单");
        SysUser user = userService.getUserInfo();
        return systemMenusDAO.getUserMenus(user.getId());
    }
}
