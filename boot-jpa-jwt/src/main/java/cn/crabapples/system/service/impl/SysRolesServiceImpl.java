package cn.crabapples.system.service.impl;

import cn.crabapples.common.DIC;
import cn.crabapples.common.PageDTO;
import cn.crabapples.system.dao.MenusDAO;
import cn.crabapples.system.dao.RolesDAO;
import cn.crabapples.system.dto.SysRolesDTO;
import cn.crabapples.system.entity.SysMenus;
import cn.crabapples.system.entity.SysRoles;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.RolesForm;
import cn.crabapples.system.service.SysRolesService;
import cn.crabapples.system.service.SysUserService;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


/**
 * TODO 系统相关服务实现类
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
public class SysRolesServiceImpl implements SysRolesService {

    @Value("${isDebug}")
    private boolean isDebug;
    private final SysUserService userService;
    private final RolesDAO rolesDAO;
    private final MenusDAO menusDAO;
    private final RedisTemplate<String, Object> redisTemplate;

    public SysRolesServiceImpl(SysUserService userService, RolesDAO rolesDAO, MenusDAO menusDAO,
                               RedisTemplate<String, Object> redisTemplate) {
        this.userService = userService;
        this.rolesDAO = rolesDAO;
        this.menusDAO = menusDAO;
        this.redisTemplate = redisTemplate;
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
     * 获取用户拥有的角色列表
     */
    @Override
    public List<SysRolesDTO> getUserRoles(HttpServletRequest request) {
        SysUser user = userService.getUserInfo(request);
        return user.getRolesList().stream().map(e -> e.toDTO(new SysRolesDTO())).collect(Collectors.toList());
    }

    /**
     * 获取角色列表(分页)
     */
    @Override
    public List<SysRoles> getRolesPage(HttpServletRequest request, PageDTO page) {
        log.info("获取[分页]角色列表:[{}]", page);
        Page<SysRoles> rolesPage = rolesDAO.findAll(page);
        Pageable pageable = rolesPage.getPageable();
        page.setDataCount(rolesDAO.count());
        page.setPageIndex(pageable.getPageNumber());
        List<SysRoles> sysRoles = rolesPage.stream().collect(Collectors.toList());
        sysRoles = setRoleMenus(sysRoles);
        log.info("返回[分页]角色列表:[{}],页码:[{}]", sysRoles, page);
        return sysRoles;
    }

    /**
     * 获取角色列表(全部)
     */
    @Override
    public List<SysRoles> getRolesList(HttpServletRequest request) {
        log.info("获取[全部]角色列表");
        List<SysRoles> sysRoles = rolesDAO.findAll();
        sysRoles = setRoleMenus(sysRoles);
        log.info("返回[全部]角色列表:[{}]", sysRoles);
        return sysRoles;
    }

    /**
     * 获取角色时设置角色拥有的菜单
     */
    private List<SysRoles> setRoleMenus(List<SysRoles> source) {
        return source.stream().peek(e -> {
            String ids = e.getMenusIds();
            if (!StringUtils.isBlank(ids)) {
                List<String> idList = JSONArray.parseArray(e.getMenusIds()).toJavaList(String.class);
                menusDAO.findByIds(idList);
                e.setSysMenus(menusDAO.findByIds(idList));
            }
        }).collect(Collectors.toList());
    }

    /**
     * 保存角色
     */
    @Override
    public SysRoles saveRoles(RolesForm form) {
        log.info("保存角色:[{}]", form);
        String id = form.getId();
        SysRoles entity;
        if (StringUtils.isBlank(id)) {
            entity = new SysRoles();
        } else {
            entity = rolesDAO.findById(form.getId());
        }
        BeanUtils.copyProperties(form, entity);
        String menusIds = JSONArray.toJSONString(form.getMenusList());
        entity.setMenusIds(menusIds);
        entity.setPermissionList(getPermissionList(form.getMenusList()));
        log.info("保存角色:[{}]", entity);
        return rolesDAO.save(entity);
    }

    /**
     * 保存角色时设置角色拥有的权限
     */
    private String getPermissionList(List<String> menusIds) {
        log.info("获取角色权限:[{}]", menusIds);
        String permissions = menusDAO.findButtonsByIds(menusIds).stream().map(SysMenus::getPermission).collect(Collectors.toList()).toString();
        log.info("角色权限:[{}]", permissions);
        return permissions;
    }

    /**
     * 删除角色
     */
    @Override
    public SysRoles removeRoles(String id) {
        log.info("删除角色:[{}]", id);
        SysRoles entity = rolesDAO.findById(id);
        entity.setDelFlag(DIC.IS_DEL);
        return rolesDAO.save(entity);
    }
}
