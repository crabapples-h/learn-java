package cn.crabapples.system.service.impl;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.DIC;
import cn.crabapples.common.utils.AssertUtils;
import cn.crabapples.common.utils.jwt.JwtConfigure;
import cn.crabapples.common.utils.jwt.JwtTokenUtils;
import cn.crabapples.system.dao.MenusDAO;
import cn.crabapples.system.dao.UserDAO;
import cn.crabapples.system.entity.SysMenus;
import cn.crabapples.system.entity.SysRoles;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.SysService;
import cn.crabapples.system.service.SysUserService;
import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
public class SysServiceImpl implements SysService {

    @Value("${isDebug}")
    private boolean isDebug;
    private final SysUserService userService;
    private final MenusDAO menusDAO;
    private final UserDAO userDAO;
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtConfigure jwtConfigure;

    public SysServiceImpl(SysUserService userService, MenusDAO menusDAO,
                          UserDAO userDAO, RedisTemplate<String, Object> redisTemplate,
                          JwtConfigure jwtConfigure) {
        this.userService = userService;
        this.menusDAO = menusDAO;
        this.userDAO = userDAO;
        this.redisTemplate = redisTemplate;
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
    public String loginCheck(UserForm form) {
        String username = form.getUsername();
        String password = MD5.create().digestHex(form.getPassword().getBytes(StandardCharsets.UTF_8));
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
        List<String> menusList = getUserMenusIds(request);
        List<SysMenus> buttons = menusDAO.findButtonsByIds(menusList);
        return buttons.stream().map(SysMenus::getPermission).collect(Collectors.toList());
    }

    /**
     * 获取当前用户所拥有的角色的所有菜单并去重
     */
    private List<String> getUserMenusIds(HttpServletRequest request) {
        SysUser user = getUserInfo(request);
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
     * 获取当前登录用户的信息
     */
    @Override
    public SysUser getUserInfo(HttpServletRequest request) {
        return userService.getUserInfo(request);
    }
}
