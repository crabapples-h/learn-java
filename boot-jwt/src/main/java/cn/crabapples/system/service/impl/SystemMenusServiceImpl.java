package cn.crabapples.system.service.impl;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.DIC;
import cn.crabapples.system.dao.MenusDAO;
import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.entity.SysRoleMenus;
import cn.crabapples.system.entity.SysRole;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.MenusForm;
import cn.crabapples.system.service.SystemMenusService;
import cn.crabapples.system.service.SystemRolesService;
import cn.crabapples.system.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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
     * 获取当前用户拥有的菜单
     * 读取用户所拥有的所有角色，将所有角色拥有的菜单id放入一个集合，然后去重
     * 最后用这个去重后的集合过滤生成菜单树
     *
     * @return 当前用户拥有的菜单
     */
    //    @Cacheable(value = "crabapples:sysMenus", key = "#auth")
    @Override
    public List<SysMenu> getUserMenus() {
        log.info("获取用户拥有的所有菜单");
        SysUser user = userService.getUserInfo();
        List<String> userMenuList = menusDAO.getUserMenus(user.getId()).stream()
                .map(SysMenu::getId).collect(Collectors.toList());
        List<SysMenu> allRootMenuTree = menusDAO.findMenusTree();
        List<SysMenu> list = filterRootMenusTree(userMenuList, allRootMenuTree);
        log.info("用户拥有的所有菜单[{}]", list);
        return list;
    }

    /**
     * 删除菜单-逻辑
     */
    @Override
    public void removeMenus(String id) {
        SysMenu entity = menusDAO.findById(id);
        entity.setDelFlag(DIC.IS_DEL);
        removeRolesMenus(id);
        menusDAO.save(entity);
    }

    /**
     * 删除菜单-物理
     */
    @Override
    public void removeReallyMenus(String id) {
        menusDAO.remove(id);
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
     * 返回前端时移除菜单树中标记为删除的数据
     */
    private List<SysMenu> filterMenusByDelFlag(List<SysMenu> sysMenus) {
        return sysMenus.stream().filter(e -> {
            List<SysMenu> children = filterMenusByDelFlag(e.getChildren());
            e.setChildren(children);
            return e.getDelFlag() == DIC.NOT_DEL;
        }).collect(Collectors.toList());
    }

    /**
     * 保存菜单并判断是增加还是编辑
     */
    @Override
    public void saveMenus(MenusForm form) {
        String id = String.valueOf(form.getId());
        log.info("id:[{}][{}]", id, StringUtils.isBlank(id));
        if (StringUtils.isBlank(id)) {
            addMenus(form);
        } else {
            editMenus(id, form);
        }
    }

    /**
     * 保存菜单时如果是编辑菜单
     */
    private void editMenus(String id, MenusForm form) {
        SysMenu entity = menusDAO.findById(id);
        BeanUtils.copyProperties(form, entity);
        log.info("修改菜单:[{}]", entity);
        menusDAO.save(entity);
    }

    /**
     * 保存菜单时如果是添加子菜单
     */
    private void addMenus(MenusForm form) {
        log.info("添加菜单,[{}]", form);
        menusDAO.save(form.toEntity());
        log.info("添加菜单完成");
    }

    @Override
    public SysRoleMenus getRoleMenus(String id) {
        throw new ApplicationException("暂未实现");
//        return menusDAO.getRoleMenus(id);
    }
}
