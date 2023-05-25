package cn.crabapples.system.service.impl;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.DIC;
import cn.crabapples.common.jwt.JwtTokenUtils;
import cn.crabapples.system.dao.MenusDAO;
import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.entity.SysRole;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.MenusForm;
import cn.crabapples.system.service.SystemMenusService;
import cn.crabapples.system.service.SystemRolesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


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
    private final SystemRolesService rolesService;
    private final MenusDAO menusDAO;
    private final JwtTokenUtils jwtTokenUtils;

    public SystemMenusServiceImpl(HttpServletRequest request,
                                  SystemRolesService rolesService, MenusDAO menusDAO, JwtTokenUtils jwtTokenUtils) {
        this.request = request;
        this.rolesService = rolesService;
        this.menusDAO = menusDAO;
        this.jwtTokenUtils = jwtTokenUtils;
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
    public List<SysMenu> getByUser() {
        SysUser userinfo = jwtTokenUtils.getUserinfo();
        List<SysRole> roleList = userinfo.getRoleList();
        List<SysMenu> list = new ArrayList<>();
        for (SysRole sysRole : roleList) {
            list.addAll(sysRole.getMenuList());
        }
//        log.info("获取用户拥有的所有菜单");
//        List<String> menusList = getUserMenusIds();
//        MenusForm form = new MenusForm();
//        form.setIsRoot(DIC.IS_ROOT);
//        List<SysMenu> allMenus = menusDAO.queryList(form);
//        List<SysMenu> list = filterRootMenusTree(menusList, allMenus);
//        log.info("用户拥有的所有菜单[{}]", list);
        return list;
    }


    /**
     * 获取当前用户所拥有的角色的所有菜单ID并去重
     */
//    private List<String> getUserMenusIds() {
////        SysUser user = systemService.getUserInfo();
//////        String roleIds = user.getRolesList();  if (StringUtils.isEmpty(roleIds)) {
////////            return Collections.EMPTY_LIST;
////////        }
//////
//////        List<SysRoles> roles = rolesService.getRolesList(request);
//////        List<SysRoles> roles = rolesService.getByIds(roleIds.split(","));
////        List<SysRole> roles = rolesService.getByIds(user.getRolesList());
////        List<String> menusIds = new ArrayList<>();
////        roles.forEach(e -> {
////            String ids = e.getMenusIds();
////            if (!StringUtils.isBlank(ids)) {
////                List<String> idList = JSONArray.parseArray(e.getMenusIds()).toJavaList(String.class);
////                menusIds.addAll(idList);
////            }
////        });
////        return menusIds.stream().distinct().collect(Collectors.toList());
//    }

    /**
     * 删除菜单-逻辑
     */
    @Override
    public void delete(String id) {
        SysMenu entity = menusDAO.findById(id);
        entity.setDelFlag(DIC.IS_DEL);
        removeRolesMenus(id);
        menusDAO.save(entity);
    }

    /**
     * 删除菜单-物理
     */
    @Override
    public void deleteReally(String id) {
        menusDAO.remove(id);
    }

    /**
     * 删除菜单后将所拥有该菜单的角色中把该菜单移除
     */
    void removeRolesMenus(String id) {
        List<SysRole> sysRoles = rolesService.findByMenusId(id);
        sysRoles.forEach(e -> {
//            String menusIds = e.getMenusIds().replace(id, "");
//            e.setMenusIds(menusIds);
            rolesService.saveRoles(e);
        });
    }

    @Override
    public List<SysMenu> getList(MenusForm form) {
        return menusDAO.queryList(form);
    }

    @Override
    public Page<SysMenu> getPage(MenusForm form) {
        PageRequest page = getJpaPage(form);
        return menusDAO.queryList(form, page);
    }

    /**
     * 保存菜单并判断是增加还是编辑
     */
    @Override
    public void save(MenusForm form) {
        String id = String.valueOf(form.getId());
        log.info("id:[{}][{}]", id, StringUtils.isEmpty(id));
        if (StringUtils.isEmpty(id)) {
//            addMenus(form);
        } else {
            editMenus(id, form);
        }
    }

    /**
     * 保存菜单时如果是编辑菜单
     */
    private void editMenus(String id, MenusForm form) {
        SysMenu entity = form.toEntity();
        BeanUtils.copyProperties(form, entity);
        log.info("修改菜单:[{}]", entity);
        menusDAO.save(entity);
    }

//    /**
//     * 保存菜单时如果是添加菜单
//     */
//    private void addMenus(MenusForm form) {
//        SysMenu entity = form.toEntity();
//        log.info("添加新菜单:[{}]", form);
//        String parentId = form.getParentId();
//        log.info("parentId:[{}][{}]", parentId, StringUtils.isEmpty(parentId));
//        if (!StringUtils.isEmpty(parentId)) {
//            addChildrenMenus(parentId, entity);
//        } else {
//            menusDAO.save(entity);
//        }
//    }

//    /**
//     * 保存菜单时如果是添加子菜单
//     */
//    private void addChildrenMenus(String prentId, SysMenu entity) {
//        log.info("添加子菜单,parentId:[{}],[{}]", prentId, entity);
//        SysMenu root = menusDAO.findById(prentId);
//        List<SysMenu> children = root.getChildren();
//        entity.setIsRoot(DIC.NOT_ROOT);
//        entity = menusDAO.save(entity);
//        children.add(entity);
//        root.setChildren(children);
//        menusDAO.save(root);
//        log.info("添加子菜单完成");
//    }


}
