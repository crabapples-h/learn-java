package cn.crabapples.system.service.impl;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.DIC;
import cn.crabapples.common.jwt.JwtConfigure;
import cn.crabapples.common.jwt.JwtTokenUtils;
import cn.crabapples.common.utils.AssertUtils;
import cn.crabapples.system.dao.MenusDAO;
import cn.crabapples.system.entity.SysMenus;
import cn.crabapples.system.entity.SysRoles;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.SystemRolesService;
import cn.crabapples.system.service.SystemService;
import cn.crabapples.system.service.SystemUserService;
import cn.hutool.crypto.digest.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    private final SystemUserService userService;
    private final SystemRolesService rolesService;
    private final MenusDAO menusDAO;
    private final JwtConfigure jwtConfigure;

    public SystemServiceImpl(SystemUserService userService, SystemRolesService rolesService,
                             MenusDAO menusDAO, JwtConfigure jwtConfigure) {
        this.userService = userService;
        this.rolesService = rolesService;
        this.menusDAO = menusDAO;
        this.jwtConfigure = jwtConfigure;
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
        SysUser sysUser = userService.findByUsername(username);
        AssertUtils.notNull(sysUser, "用户名不存在");
        if (sysUser.getStatus() == DIC.USER_LOCK) {
            throw new ApplicationException("账户已被锁定，请联系管理员");
        }
        if (sysUser.getPassword().equals(password)) {
            return JwtTokenUtils.createJWT(sysUser.getId(), sysUser.getUsername(), jwtConfigure);
        }
        throw new ApplicationException("密码错误");
    }


    /**
     * 获取用户拥有的权限
     */
    @Override
    public List<String> getUserPermissions(HttpServletRequest request) {
        log.info("获取用户拥有的所有权限");
        List<String> menusList = getUserMenusIds();
        if (menusList.isEmpty()) {
            return Collections.emptyList();
        }
        List<SysMenus> buttons = menusDAO.findButtonsByIds1(menusList);
        return buttons.stream().map(SysMenus::getPermission).collect(Collectors.toList());
    }

    /**
     * 获取当前用户所拥有的角色的所有菜单并去重
     */
    private List<String> getUserMenusIds() {
        SysUser user = userService.getUserInfo();
//        String rolesIds = user.getRolesList();
//        List<SysRoles> roles = rolesService.getByIds(rolesIds.split(","));
        final List<String> rolesList = user.getRolesList();
        if (rolesList.isEmpty()) {
            return Collections.emptyList();
        }
        List<SysRoles> roles = rolesService.getByIds(rolesList);
        List<String> menusId = new ArrayList<>();
        roles.forEach(e -> {
            List<String> idList = e.getMenusIds();
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
}
