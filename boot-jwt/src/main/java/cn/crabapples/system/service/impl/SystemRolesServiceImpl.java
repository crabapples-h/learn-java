package cn.crabapples.system.service.impl;

import cn.crabapples.system.dao.MenusDAO;
import cn.crabapples.system.dao.RolesDAO;
import cn.crabapples.system.dto.SysRolesDTO;
import cn.crabapples.system.entity.SysMenus;
import cn.crabapples.system.entity.SysRoles;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.RolesForm;
import cn.crabapples.system.service.SystemRolesService;
import cn.crabapples.system.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
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

    private final HttpServletRequest request;
    private final RolesDAO rolesDAO;
    private final MenusDAO menusDAO;
    private final SystemUserService userService;

    public SystemRolesServiceImpl(RolesDAO rolesDAO, MenusDAO menusDAO, StringRedisTemplate redisTemplate,
                                  HttpServletRequest request, SystemUserService userService) {
        this.rolesDAO = rolesDAO;
        this.menusDAO = menusDAO;
        this.request = request;
        this.userService = userService;
    }

    /**
     * 获取当前用户拥有的角色列表
     */
    @Override
    public List<SysRolesDTO> getUserRoles() {
        SysUser user = userService.getUserInfo();
        List<String> rolesList = user.getRolesList();
        if (rolesList.isEmpty()) {
            return Collections.emptyList();
        }
        List<SysRoles> roles = rolesDAO.findByIds(rolesList);
        return roles.stream().map(e -> e.toDTO(new SysRolesDTO())).collect(Collectors.toList());
    }

    /**
     * 获取角色列表(分页)
     */
    @Override
    public Iterable<SysRoles> getRolesList(Integer pageIndex, Integer pageSize, RolesForm form) {
        return rolesDAO.findAll(pageIndex, pageSize, form);
    }

    @Override
    public List<SysRoles> getByIds(List<String> ids) {
        return rolesDAO.findByIds(ids);
    }

    /**
     * 保存角色
     */
    @Override
    public boolean saveRoles(RolesForm form) {
        log.info("保存角色:[{}]", form);
        SysRoles entity = StringUtils.isBlank(form.getId()) ? new SysRoles() : rolesDAO.findById(form.getId());
        BeanUtils.copyProperties(form, entity);
        entity.setMenusIds(form.getMenusList());
        List<String> permissionList = getPermissionList(form.getMenusList());
        entity.setPermissionList(permissionList);
        log.info("保存角色:[{}]", entity);
        return rolesDAO.save(entity);
    }

    /**
     * 获取菜单下拥有的权限
     * 保存角色时设置角色拥有的权限
     */
    private List<String> getPermissionList(List<String> menusIds) {
        log.info("获取角色权限:[{}]", menusIds);
        List<SysMenus> buttonsByIds = menusDAO.findButtonsByIds1(menusIds);
        List<String> permissions = buttonsByIds.stream().map(SysMenus::getPermission).collect(Collectors.toList());
        log.info("角色权限:[{}]", permissions);
        return permissions;
    }

    /**
     * 删除角色
     */
    @Override
    public boolean removeRoles(String id) {
        return rolesDAO.deleteById(id);
    }

    @Override
    public List<SysRoles> findByMenusId(String id) {
        return rolesDAO.findByMenusId(id);
    }
}
