package cn.crabapples.system.system.service.impl;

import cn.crabapples.common.base.ApplicationException;
import cn.crabapples.common.dic.DIC;
import cn.crabapples.common.jwt.JwtTokenUtils;
import cn.crabapples.common.utils.AssertUtils;
import cn.crabapples.system.dto.SysOptionDTO;
import cn.crabapples.system.sysDict.service.SystemDictService;
import cn.crabapples.system.sysMenu.entity.SysMenu;
import cn.crabapples.system.sysRole.entity.SysRole;
import cn.crabapples.system.sysRole.service.SystemRolesService;
import cn.crabapples.system.sysRoleMenu.service.SystemRoleMenusService;
import cn.crabapples.system.sysUser.entity.SysUser;
import cn.crabapples.system.sysUser.form.UserForm;
import cn.crabapples.system.sysUser.service.SystemUserService;
import cn.crabapples.system.system.service.SystemService;
import cn.hutool.crypto.digest.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
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
public class SystemServiceImpl implements SystemService {
    @Value("${isDebug}")
    private boolean isDebug;
    @Value("${isCrypt}")
    private boolean isCrypt;
    private final SystemDictService dictService;
    private final SystemUserService userService;
    private final SystemRolesService rolesService;
    private final SystemRoleMenusService roleMenusService;
    private final JwtTokenUtils jwtTokenUtils;


    public SystemServiceImpl(SystemDictService dictService, SystemUserService userService, SystemRolesService rolesService,
                             SystemRoleMenusService roleMenusService,
                             JwtTokenUtils jwtTokenUtils) {
        this.dictService = dictService;
        this.userService = userService;
        this.rolesService = rolesService;
        this.roleMenusService = roleMenusService;
        this.jwtTokenUtils = jwtTokenUtils;
    }


    /**
     * Cacheable
     * * key: redis中key的值
     * * value: redis中key的前缀
     * * 例:
     * * key::value:tom
     * * userLogin::${#p0.username}
     * <p>
     * 用户登录验证
     *
     * @param form 用户信息
     * @return token
     */
//    @Cacheable(value = "login:token", key = "#p0.username")
    @Override
    public String login(UserForm form) {
        String username = form.getUsername();
        String password = form.getPassword();
        if (isCrypt) {
            password = MD5.create().digestHex(form.getPassword().getBytes(StandardCharsets.UTF_8));
        }
        log.info("开始登录->用户名:[{}],密码:[{}]", username, password);
//        SysUser sysUser = TenantManager.withoutTenantCondition(() -> userService.findByUsername(username));
        SysUser sysUser = userService.findByUsername(username);
        AssertUtils.notNull(sysUser, "用户名不存在");
        if (sysUser.getStatus() == DIC.USER_LOCK) {
            throw new ApplicationException("账户已被锁定，请联系管理员");
        }
        if (sysUser.getPassword().equals(password)) {
            return jwtTokenUtils.createToken(sysUser.getId(), sysUser.getUsername());
        }
        throw new ApplicationException("密码错误");
    }


    /**
     * 获取用户拥有的权限
     * (权限只针对按钮才有)
     */
    @Override
    public List<String> getUserPermissions() {
        log.info("获取用户拥有的所有权限");
        List<SysRole> userRoleList = rolesService.getUserRoles();
        List<String> roleIds = userRoleList.stream().map(SysRole::getId).collect(Collectors.toList());
        List<SysMenu> roleMenuList = roleMenusService.getRoleMenusList(roleIds);
        return roleMenuList.stream()
                .filter(e -> DIC.MENUS_TYPE_BUTTON == e.getMenusType())
                .map(SysMenu::getPermission).collect(Collectors.toList());
    }

    /**
     * 获取当前用户所拥有的角色的所有菜单并去重
     */
    private List<String> getUserMenusIds() {
        SysUser user = userService.getUserInfo();
//        String rolesIds = user.getRolesList();
//        List<SysRoles> roles = rolesService.getByIds(rolesIds.split(","));
        List<String> rolesList = user.getRoleList();
        if (rolesList.isEmpty()) {
            return Collections.emptyList();
        }
        List<SysRole> roles = rolesService.getByIds(rolesList);
        List<String> menusId = new ArrayList<>();
        roles.forEach(e -> {
            List<String> idList = e.getMenuList().stream()
                    .map(SysMenu::getId).collect(Collectors.toList());
            menusId.addAll(idList);
        });
        return menusId.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public boolean checkUsername(String username) {
        SysUser user = userService.findByUsername(username);
        AssertUtils.isNull(user, "用户名已被使用");
        return true;
    }

    @Override
    public List<SysOptionDTO> dictSelectOptions(String code) {
        return dictService.getDictItemListByCode(code)
                .stream()
                .map(e -> new SysOptionDTO()
                        .setId(e.getId())
                        .setLabel(e.getText())
                        .setValue(e.getValue()))
                .collect(Collectors.toList());
    }
}
