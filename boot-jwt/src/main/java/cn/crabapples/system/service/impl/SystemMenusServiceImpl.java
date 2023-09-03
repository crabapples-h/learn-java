package cn.crabapples.system.service.impl;

import cn.crabapples.system.dao.MenusDAO;
import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.entity.SysRole;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.MenusForm;
import cn.crabapples.system.service.SystemMenusService;
import cn.crabapples.system.service.SystemRolesService;
import cn.crabapples.system.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
    private final SystemRolesService rolesService;
    private final MenusDAO menusDAO;

    public SystemMenusServiceImpl(HttpServletRequest request, MenusDAO menusDAO,
                                  SystemUserService userService, SystemRolesService rolesService) {
        this.request = request;
        this.menusDAO = menusDAO;
        this.userService = userService;
        this.rolesService = rolesService;
    }

    /**
     * 获取当前用户拥有的菜单树
     * 读取用户所拥有的所有角色，将所有角色拥有的菜单id放入一个集合，然后去重
     * 最后用这个去重后的集合过滤生成菜单树
     *
     * @return 当前用户拥有的菜单
     */
    //    @Cacheable(value = "crabapples:sysMenus", key = "#auth")
    @Override
    public List<SysMenu> getUserMenusTree() {
        log.info("获取用户拥有的所有菜单");
        SysUser user = userService.getUserInfo();
        List<SysMenu> userMenus = menusDAO.getUserMenus(user.getId());
        List<String> userMenuList = userMenus.stream()
                .map(SysMenu::getId).collect(Collectors.toList());
        List<SysMenu> allRootMenuTree = menusDAO.findMenusTree();
        List<SysMenu> list = filterRootMenusTree(userMenuList, allRootMenuTree);
        log.info("用户拥有的所有菜单[{}]", list);
        return list;
    }

    @Override
    public boolean removeMenus(String id) {
        return menusDAO.remove(id);
    }

    /**
     * 保存菜单
     *
     * @return 是否保存成功
     */
    @Override
    public boolean saveMenus(MenusForm form) {
        return menusDAO.save(form.toEntity());
    }

    /**
     * 获取菜单列表(全部)
     */
    @Override
    public List<SysMenu> getMenusList() {
        List<SysMenu> sysMenus = menusDAO.findMenusTree();
        sysMenus = filterMenusByDelFlag(sysMenus);
        return sysMenus;
    }


    /**
     * 删除菜单后将所拥有该菜单的角色中把该菜单移除
     */
    void removeRolesMenus(String id) {
        List<SysRole> sysRoles = rolesService.findByMenusId(id);
        sysRoles.forEach(e -> {
//            e.getMenusIds().remove(id);
//            rolesService.saveRoles(e);
        });
    }



}
