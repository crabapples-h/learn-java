package cn.crabapples.system.service.impl;

import cn.crabapples.common.DIC;
import cn.crabapples.common.PageDTO;
import cn.crabapples.common.jwt.JwtTokenUtils;
import cn.crabapples.system.dao.MenusDAO;
import cn.crabapples.system.dao.RoleDAO;
import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.entity.SysRole;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.RolesForm;
import cn.crabapples.system.service.SystemRolesService;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


/**
 * TODO 系统相关服务实现类[角色]
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
public class SystemRolesServiceImpl implements SystemRolesService {

    private final RoleDAO roleDAO;
    private final MenusDAO menusDAO;
    private final JwtTokenUtils jwtTokenUtils;

    public SystemRolesServiceImpl(RoleDAO roleDAO, MenusDAO menusDAO,
                                  RedisTemplate<String, Object> redisTemplate, JwtTokenUtils jwtTokenUtils) {
        this.roleDAO = roleDAO;
        this.menusDAO = menusDAO;
        this.jwtTokenUtils = jwtTokenUtils;
    }

//    /**
//     * 获取用户拥有的角色列表
//     */
//    @Override
//    public List<SysRolesDTO> getUserRoles(HttpServletRequest request) {
//        SysUser user = systemService.getUserInfo(request);
//        return user.getRolesList().stream().map(e -> e.toDTO(new SysRolesDTO())).collect(Collectors.toList());
//    }

    /**
     * 获取角色列表(分页)
     */
    @Override
    public List<SysRole> getRolesPage(HttpServletRequest request, PageDTO page) {
        log.info("获取[分页]角色列表:[{}]", page);
        Page<SysRole> rolesPage = roleDAO.findAll(page);
        Pageable pageable = rolesPage.getPageable();
        page.setDataCount(roleDAO.count());
        page.setPageIndex(pageable.getPageNumber());
        List<SysRole> sysRoles = rolesPage.stream().collect(Collectors.toList());
//        sysRoles = setRoleMenus(sysRoles);
        log.info("返回[分页]角色列表:[{}],页码:[{}]", sysRoles, page);
        return sysRoles;
    }

    /**
     * 获取角色列表(全部)
     */
    @Override
    public List<SysRole> getRolesList(HttpServletRequest request) {
        log.info("获取[全部]角色列表");
        List<SysRole> sysRoles = roleDAO.findAll();
//        sysRoles = setRoleMenus(sysRoles);
        log.info("返回[全部]角色列表:[{}]", sysRoles);
        return sysRoles;
    }

    @Override
    public List<SysRole> getByIds(List<String> ids) {
        return roleDAO.findByIds(ids);
    }

    @Override
    public List<SysRole> getByIds(String[] ids) {
        return roleDAO.findByIds(ids);
    }

    @Override
    public SysRole getById(String id) {
        return roleDAO.findById(id);
    }

    /**
     * 获取角色时设置角色拥有的菜单
     */
//    private List<SysRole> setRoleMenus(List<SysRole> source) {
//        return source.stream().peek(e -> {
//            String ids = e.getMenusIds();
//            if (!StringUtils.isBlank(ids)) {
//                List<String> idList = JSONArray.parseArray(e.getMenusIds()).toJavaList(String.class);
//                menusDAO.findByIds(idList);
//                e.setSysMenus(menusDAO.findByIds(idList));
//            }
//        }).collect(Collectors.toList());
//    }

    /**
     * 保存角色
     */
    @Override
    @Transactional
    public SysRole saveRoles(RolesForm form) {
        log.info("保存角色:[{}]", form);
        String id = form.getId();
        SysRole entity;
        if (StringUtils.isBlank(id)) {
            entity = new SysRole();
        } else {
            entity = roleDAO.findById(form.getId());
        }
        BeanUtils.copyProperties(form, entity);
        String menusIds = JSONArray.toJSONString(form.getMenusList());
//        entity.setMenusIds(menusIds);
//        entity.setPermissionList(getPermissionList(form.getMenusList()));
        log.info("保存角色:[{}]", entity);
        return roleDAO.save(entity);
    }

    @Override
    public SysRole saveRoles(SysRole e) {
        return roleDAO.save(e);
    }

    /**
     * 保存角色时设置角色拥有的权限
     */
    private String getPermissionList(List<String> menusIds) {
        log.info("获取角色权限:[{}]", menusIds);
        String permissions = menusDAO.findButtonsByIds(menusIds).stream().map(SysMenu::getPermission).collect(Collectors.toList()).toString();
        log.info("角色权限:[{}]", permissions);
        return permissions;
    }

    /**
     * 删除角色
     */
    @Override
    public SysRole removeRoles(String id) {
        log.info("删除角色:[{}]", id);
        SysRole entity = roleDAO.findById(id);
        entity.setDelFlag(DIC.IS_DEL);
        return roleDAO.save(entity);
    }

    @Override
    public List<SysRole> findByMenusId(String id) {
        return roleDAO.findByMenusId(id);
    }

    @Override
    public List<SysRole> getByUser() {
        SysUser userinfo = jwtTokenUtils.getUserinfo();
        return userinfo.getRoleList();
    }
}
