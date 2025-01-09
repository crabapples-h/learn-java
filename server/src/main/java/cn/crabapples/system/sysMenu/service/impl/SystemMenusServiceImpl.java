package cn.crabapples.system.sysMenu.service.impl;

import cn.crabapples.system.sysMenu.dao.SystemMenusDAO;
import cn.crabapples.system.sysMenu.entity.SysMenu;
import cn.crabapples.system.sysMenu.form.MenusForm;
import cn.crabapples.system.sysMenu.service.SystemMenusService;
import cn.crabapples.system.sysRoleMenu.service.SystemRoleMenusService;
import cn.crabapples.system.sysUser.entity.SysUser;
import cn.crabapples.system.sysUser.service.SystemUserService;
import com.mybatisflex.core.paginate.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private final SystemRoleMenusService roleMenusService;
    private final SystemMenusDAO systemMenusDAO;

    public SystemMenusServiceImpl(HttpServletRequest request, SystemMenusDAO systemMenusDAO,
                                  SystemUserService userService, SystemRoleMenusService roleMenusService) {
        this.request = request;
        this.systemMenusDAO = systemMenusDAO;
        this.userService = userService;
        this.roleMenusService = roleMenusService;
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
        log.debug("获取用户拥有的所有菜单");
        SysUser user = userService.getUserInfo();
        List<SysMenu> userMenus = systemMenusDAO.getUserMenus(user.getId());
        List<String> userMenuIds = userMenus.stream()
                .map(SysMenu::getId).collect(Collectors.toList());
        List<SysMenu> allRootMenuTree = systemMenusDAO.findMenusTree();
//        Utils.saveObj(allRootMenuTree, "allRootMenuTree");
        List<SysMenu> list = filterRootMenusTree(userMenuIds, allRootMenuTree);
        log.debug("用户拥有的所有菜单[{}]", list);
        return list;
    }



    @Override
    public boolean removeMenus(String id) {
        return systemMenusDAO.remove(id);
    }

    /**
     * 保存菜单
     *
     * @return 是否保存成功
     */
    @Override
    public boolean saveMenus(MenusForm form) {
        if (!StringUtils.hasLength(form.getPid())) {
            // 当添加子菜单时，将其父级菜单从已拥有的权限中移除，避免角色直接拥有新添加的菜单的权限
            roleMenusService.delByMenuId(form.getPid());
        }
        return systemMenusDAO.saveOrUpdate(form.toEntity());
    }

    /**
     * 获取菜单列表(全部)
     */
    @Override
    public List<SysMenu> getMenusList() {
        return systemMenusDAO.findMenusTree();
    }

    @Override
    public Page<SysMenu> getMenuPage(Integer pageIndex, Integer pageSize, MenusForm form) {
        return systemMenusDAO.getMenuPage(Page.of(pageIndex, pageSize));
    }
}
