package cn.crabapples.common.config;

import cn.crabapples.common.utils.AssertUtils;
import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.service.SysService;
import cn.crabapples.system.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * TODO shiro配置
 *
 * @author Mr.He
 * 2020/3/5 1:03
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Configuration
public class ShiroRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
    private final UserService userService;
    private final SysService sysService;

    public ShiroRealm(UserService userService, SysService sysService) {
        this.userService = userService;
        this.sysService = sysService;
    }

    /**
     * shiro授权调用的方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        List<SysMenu> menus = sysService.getSysMenus(user);
        Set<String> permissions = getPermissions(menus).stream().filter(Objects::nonNull).collect(Collectors.toSet());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 获取用户权限
     *
     * @param menus 用户菜单列表
     * @return 用户权限列表
     */
    private Set<String> getPermissions(List<SysMenu> menus) {
        Set<String> permissions = new HashSet<>();
        menus.forEach(e -> {
            permissions.addAll(getPermissions(e.getChildren()));
            permissions.add(e.getPermission());
        });
        return permissions;
    }

    /**
     * shiro认证(登录)调用的方法,认证失败时会抛出异常
     * IncorrectCredentialsException密码错误
     * UnknownAccountException用户名错误
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = String.valueOf(authenticationToken.getPrincipal());
        logger.debug("开始对:[{}]执行shiro认证", username);
        SysUser user = userService.findByUsername(username);
        AssertUtils.notNull(user, "用户不存在");
        AssertUtils.isEquals(user.getStatus(), 0, "用户已被冻结");
        logger.debug("用户:[{}]shiro认证结束", username);
        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        System.err.println(info);
        return info;

    }
}
