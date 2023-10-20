package cn.crabapples.system.service.impl;

import cn.crabapples.system.dao.MenusDAO;
import cn.crabapples.system.dao.RolesDAO;
import cn.crabapples.system.dto.SysRolesDTO;
import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.entity.SysRole;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.RolesForm;
import cn.crabapples.system.service.SystemRoleMenusService;
import cn.crabapples.system.service.SystemRolesService;
import cn.crabapples.system.service.SystemUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    private final SystemRoleMenusService roleMenusService;

    public SystemRolesServiceImpl(RolesDAO rolesDAO, MenusDAO menusDAO, StringRedisTemplate redisTemplate,
                                  HttpServletRequest request, SystemUserService userService, SystemRoleMenusService roleMenusService) {
        this.rolesDAO = rolesDAO;
        this.menusDAO = menusDAO;
        this.request = request;
        this.userService = userService;
        this.roleMenusService = roleMenusService;
    }

    // 获取当前用户的角色信息
    public List<SysRole> getUserRoles() {
        SysUser user = userService.getUserInfo();
        return rolesDAO.getUserRoles(user.getId());
    }

    // 获取当前用户的角色信息 DTO
    public List<SysRolesDTO> getUserRolesDTO() {
        SysUser user = userService.getUserInfo();
        return rolesDAO.getUserRolesDTO(user.getId());
    }

    @Override
    public List<SysRolesDTO> getUserRolesById(String id) {
        return rolesDAO.getUserRolesDTO(id);
    }

    /**
     * 获取角色列表(不分页)
     */
    @Override
    public List<SysRole> getRolesList(RolesForm form) {
        return rolesDAO.findAll(form);
    }

    /**
     * 获取角色列表(分页)
     */
    @Override
    public IPage<SysRole> getRolesPage(Integer pageIndex, Integer pageSize, RolesForm form) {
        return rolesDAO.findAll(pageIndex, pageSize, form);
    }

    @Override
    public List<SysRole> getByIds(List<String> ids) {
        return rolesDAO.findByIds(ids);
    }

    /**
     * 保存角色
     */
    @Override
    public boolean saveRoles(RolesForm form) {
        log.info("保存角色:[{}]", form);
        SysRole entity = form.toEntity();
        boolean status = entity.insertOrUpdate();
        roleMenusService.saveRoleMenus(entity.getId(), form.getMenuList());
        return status;
    }

    /**
     * 获取菜单下拥有的权限
     * 保存角色时设置角色拥有的权限
     */
    private List<String> getPermissionList(List<String> menusIds) {
        log.info("获取角色权限:[{}]", menusIds);
        List<SysMenu> buttonsByIds = menusDAO.findButtonsByIds(menusIds);
        List<String> permissions = buttonsByIds.stream().map(SysMenu::getPermission).collect(Collectors.toList());
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
}
