package cn.crabapples.system.service.impl;

import cn.crabapples.common.DIC;
import cn.crabapples.common.PageDTO;
import cn.crabapples.system.dao.MenusDAO;
import cn.crabapples.system.entity.SysMenus;
import cn.crabapples.system.entity.SysRoles;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.MenusForm;
import cn.crabapples.system.service.SystemMenusService;
import cn.crabapples.system.service.SystemRolesService;
import cn.crabapples.system.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    public SystemMenusServiceImpl(HttpServletRequest request, SystemUserService userService,
                                  SystemRolesService rolesService, MenusDAO menusDAO) {
        this.request = request;
        this.userService = userService;
        this.rolesService = rolesService;
        this.menusDAO = menusDAO;
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
    public List<SysMenus> getUserMenus() {
        log.info("获取用户拥有的所有菜单");
        List<String> userMenuList = getUserMenusIds();
        List<SysMenus> allRootMenuTree = menusDAO.findMenusTree();
        List<SysMenus> list = filterRootMenusTree(userMenuList, allRootMenuTree);
        log.info("用户拥有的所有菜单[{}]", list);
        return list;
    }


    /**
     * 获取当前用户所拥有的角色的所有菜单ID并去重
     */
    private List<String> getUserMenusIds() {
        SysUser user = userService.getUserInfo();
        List<SysRoles> roles = rolesService.getByIds(user.getRolesList());
        List<String> menusIds = new ArrayList<>();
        roles.forEach(e -> {
            List<String> idList = e.getMenusIds();
            menusIds.addAll(idList);
        });
        return menusIds.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 删除菜单-逻辑
     */
    @Override
    public void removeMenus(String id) {
        SysMenus entity = menusDAO.findById(id);
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
        List<SysRoles> sysRoles = rolesService.findByMenusId(id);
        sysRoles.forEach(e -> {
            e.getMenusIds().remove(id);
            rolesService.saveRoles(e);
        });
    }

    /**
     * 获取菜单列表(分页)
     */
    @Override
    public List<SysMenus> getMenusPage(PageDTO page) {
        Page<SysMenus> menusPage = menusDAO.findMenusTree(page);
        Pageable pageable = menusPage.getPageable();
        page.setDataCount(menusDAO.count());
        page.setPageIndex(pageable.getPageNumber());
        List<SysMenus> sysMenus = menusPage.stream().collect(Collectors.toList());
        sysMenus = filterMenusByDelFlag(sysMenus);
        return sysMenus;
    }

    /**
     * 获取菜单列表(全部)
     */
    @Override
    public List<SysMenus> getMenusList() {
        List<SysMenus> sysMenus = menusDAO.findMenusTree();
        sysMenus = filterMenusByDelFlag(sysMenus);
        return sysMenus;
    }

    /**
     * 返回前端时移除菜单树中标记为删除的数据
     */
    private List<SysMenus> filterMenusByDelFlag(List<SysMenus> sysMenus) {
        return sysMenus.stream().filter(e -> {
            List<SysMenus> children = filterMenusByDelFlag(e.getChildren());
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
        SysMenus entity = menusDAO.findById(id);
        BeanUtils.copyProperties(form, entity);
        log.info("修改菜单:[{}]", entity);
        menusDAO.save(entity);
    }

    /**
     * 保存菜单时如果是添加菜单
     */
    private void addMenus(MenusForm form) {
        SysMenus entity = new SysMenus();
        BeanUtils.copyProperties(form, entity);
        log.info("添加新菜单:[{}]", form);
        String parentId = form.getParentId();
        log.info("parentId:[{}][{}]", parentId, StringUtils.isBlank(parentId));
        if (!StringUtils.isBlank(parentId)) {
            addChildrenMenus(parentId, entity);
        } else {
            menusDAO.save(entity);
        }
    }

    /**
     * 保存菜单时如果是添加子菜单
     */
    private void addChildrenMenus(String prentId, SysMenus entity) {
        log.info("添加子菜单,parentId:[{}],[{}]", prentId, entity);
        SysMenus root = menusDAO.findById(prentId);
        List<SysMenus> children = root.getChildren();
        entity.setIsRoot(DIC.NOT_ROOT);
        entity = menusDAO.save(entity);
        children.add(entity);
        root.setChildren(children);
        menusDAO.save(root);
        log.info("添加子菜单完成");
    }
}