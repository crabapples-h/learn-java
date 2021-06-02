package cn.crabapples.system.service.impl;

import cn.crabapples.common.DIC;
import cn.crabapples.common.PageDTO;
import cn.crabapples.system.dao.MenusDAO;
import cn.crabapples.system.dao.RolesDAO;
import cn.crabapples.system.entity.SysMenus;
import cn.crabapples.system.entity.SysRoles;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.MenusForm;
import cn.crabapples.system.service.SysMenusService;
import cn.crabapples.system.service.SysUserService;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
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
public class SysMenusServiceImpl implements SysMenusService {

    private final SysUserService userService;
    private final RolesDAO rolesDAO;
    private final MenusDAO menusDAO;
    private final RedisTemplate<String, Object> redisTemplate;

    public SysMenusServiceImpl(SysUserService userService, RolesDAO rolesDAO, MenusDAO menusDAO,
                               RedisTemplate<String, Object> redisTemplate
    ) {
        this.userService = userService;
        this.rolesDAO = rolesDAO;
        this.menusDAO = menusDAO;
        this.redisTemplate = redisTemplate;
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
    public List<SysMenus> getUserMenus(HttpServletRequest request) {
        log.info("获取用户拥有的所有菜单");
        List<String> menusList = getUserMenusIds(request);
        List<SysMenus> allMenus = menusDAO.findRoot();
        List<SysMenus> list = filterRootMenusTree(menusList, allMenus);
        log.info("用户拥有的所有菜单[{}]", list);
        return list;
    }


    /**
     * 获取当前用户所拥有的角色的所有菜单ID并去重
     */
    private List<String> getUserMenusIds(HttpServletRequest request) {
        SysUser user = userService.getUserInfo(request);
        List<SysRoles> roles = user.getRolesList();
        List<String> menusId = new ArrayList<>();
        roles.forEach(e -> {
            String ids = e.getMenusIds();
            if (!StringUtils.isBlank(ids)) {
                List<String> idList = JSONArray.parseArray(e.getMenusIds()).toJavaList(String.class);
                menusId.addAll(idList);
            }
        });
        return menusId.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 删除菜单
     */
    @Override
    public SysMenus removeMenus(String id) {
        SysMenus entity = menusDAO.findById(id);
        entity.setDelFlag(DIC.IS_DEL);
        removeRolesMenus(id);
        return menusDAO.save(entity);
    }

    /**
     * 删除菜单后将所拥有该菜单的角色中把该菜单移除
     */
    void removeRolesMenus(String id) {
        List<SysRoles> sysRoles = rolesDAO.findByMenusId(id);
        sysRoles.forEach(e -> {
            String menusIds = e.getMenusIds().replace(id, "");
            e.setMenusIds(menusIds);
            rolesDAO.save(e);
        });
    }

    /**
     * 获取菜单列表(分页)
     */
    @Override
    public List<SysMenus> getMenusPage(HttpServletRequest request, PageDTO page) {
        Page<SysMenus> menusPage = menusDAO.findRoot(page);
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
    public List<SysMenus> getMenusList(HttpServletRequest request) {
        List<SysMenus> sysMenus = menusDAO.findRoot();
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
    public SysMenus saveMenus(MenusForm form) {
        String id = String.valueOf(form.getId());
        log.info("id:[{}][{}]", id, StringUtils.isBlank(id));
        if (StringUtils.isBlank(id)) {
            return addMenus(form);
        } else {
            return editMenus(id, form);
        }
    }

    /**
     * 保存菜单时如果是编辑菜单
     */
    private SysMenus editMenus(String id, MenusForm form) {
        SysMenus entity = menusDAO.findById(id);
        BeanUtils.copyProperties(form, entity);
        log.info("修改菜单:[{}]", entity);
        return menusDAO.save(entity);
    }

    /**
     * 保存菜单时如果是添加菜单
     */
    private SysMenus addMenus(MenusForm form) {
        SysMenus entity = new SysMenus();
        BeanUtils.copyProperties(form, entity);
        log.info("添加新菜单:[{}]", form);
        String parentId = form.getParentId();
        log.info("parentId:[{}][{}]", parentId, StringUtils.isBlank(parentId));
        if (!StringUtils.isBlank(parentId)) {
            return addChildrenMenus(parentId, entity);
        } else {
            return menusDAO.save(entity);
        }
    }

    /**
     * 保存菜单时如果是添加子菜单
     */
    private SysMenus addChildrenMenus(String prentId, SysMenus entity) {
        log.info("添加新子菜单,parentId:[{}],[{}]", prentId, entity);
        SysMenus root = menusDAO.findById(prentId);
        List<SysMenus> children = root.getChildren();
        entity.setIsRoot(DIC.NOT_ROOT);
        entity = menusDAO.save(entity);
        children.add(entity);
        root.setChildren(children);
        return menusDAO.save(root);
    }


}
