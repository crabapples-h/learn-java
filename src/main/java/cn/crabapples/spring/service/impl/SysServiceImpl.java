package cn.crabapples.spring.service.impl;

import cn.crabapples.spring.common.config.ApplicationConfigure;
import cn.crabapples.spring.dao.SysRepository;
import cn.crabapples.spring.dto.ResponseDTO;
import cn.crabapples.spring.entity.SysMenu;
import cn.crabapples.spring.entity.SysRole;
import cn.crabapples.spring.entity.SysUser;
import cn.crabapples.spring.form.UserForm;
import cn.crabapples.spring.service.SysService;
import cn.crabapples.spring.service.UserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

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
//@CacheConfig(cacheNames = "user:")
public class SysServiceImpl implements SysService {

    private static final Logger logger = LoggerFactory.getLogger(SysServiceImpl.class);
    private String aesKey;
    private String redisPrefix;
    private Long tokenCacheTime;
    private String salt;

    private final UserService userService;

    private final SysRepository sysRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    public SysServiceImpl(ApplicationConfigure applicationConfigure,
                          UserService userService,
                          SysRepository sysRepository,
                          RedisTemplate<String, Object> redisTemplate) {
        this.userService = userService;
        this.sysRepository = sysRepository;
        this.redisTemplate = redisTemplate;
        this.aesKey = applicationConfigure.AES_KEY;
        this.redisPrefix = applicationConfigure.REDIS_PREFIX;
        this.tokenCacheTime = applicationConfigure.TOKEN_CACHE_TIME;
        this.salt = applicationConfigure.SALT;
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
    public ResponseDTO login(UserForm form) {
        try {
            String username = form.getUsername();
            String password = new Md5Hash(form.getPassword(), salt).toString();
            logger.info("开始登录->用户名:[{}],密码:[{}]", username, password);
            SysUser user = shiroCheckLogin(username, password);
            if (user == null) {
                return ResponseDTO.returnError("用户名或密码错");
            }
        } catch (Exception e) {
            logger.warn("登录失败:[{}]", e.getMessage(), e);
            return ResponseDTO.returnError(e.getMessage());
        }
        return ResponseDTO.returnSuccess("登录成功");
    }

    /**
     * shiro认证
     *
     * @param username 用户名
     * @param password 密码
     * @return 认证成功后的用户对象
     */
    private SysUser shiroCheckLogin(String username, String password) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
            return (SysUser) subject.getPrincipal();
        } catch (IncorrectCredentialsException e) {
            logger.warn("shiro认证失败,密码错误:[{}]", e.getMessage());
        } catch (UnknownAccountException e) {
            logger.warn("shiro认证失败,用户不存在:[{}]", e.getMessage());
        } catch (Exception e) {
            logger.error("shiro认证失败", e);
        }
        return null;
    }

    /**
     * 获取当前用户拥有的菜单
     * @return 当前用户拥有的菜单
     */
    //    @Cacheable(value = "crabapples:sysMenus", key = "#auth")
    @Override
    public Set<? extends Object> getSysMenus() {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        logger.info("开始获取用户:[{}]已授权的菜单", user.getId());
        Set<SysRole> sysRoles = user.getSysRoles();
        Set<SysMenu> sysMenus = new HashSet<>();
        sysRoles.forEach(e -> sysMenus.addAll(e.getSysMenus()));
        logger.info("用户:[{}]菜单获取成功,即将开始格式化菜单", user.getId());
        return formatChildrenMenus(Lists.newArrayList(sysMenus));
    }

    @SuppressWarnings("all")
    public static void main(String[] args) {
        File file = new File("d:/2");
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            Set<SysMenu> menus = (Set<SysMenu>) objectInputStream.readObject();
            List<SysMenu> list = Lists.newArrayList(menus);
            System.err.println(formatChildrenMenus(list));
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * 格式化菜单为树状
     * @param menus 菜单列表
     * @return 格式化后的菜单Set
     */
    private static Set<Object> formatChildrenMenus(List<SysMenu> menus) {
        logger.info("开始格式化菜单");
        MenusFormat menusFormat = (menu, menuList) -> {
            logger.info("当前正在格式化的菜单为:[{}]", menu);
            Set<SysMenu> children = new HashSet<>();
            for (int i = menuList.size() - 1; i >= 0; i--) {
                if (menuList.get(i).getParentId() != null && menuList.get(i).getParentId().equals(menu.getId())) {
                    children.add(menuList.get(i));
                    menuList.remove(menuList.get(i));
                }
            }
            menu.setChildren(children);
            return menu;
        };
        List<SysMenu> tempMenus = Lists.newArrayList(menus);
        Set<SysMenu> sysMenus = new HashSet<>();
        for (int i = tempMenus.size() - 1; i >= 0; i--) {
            if (menus.get(i).getParentId() == null) {
                sysMenus.add(menusFormat.format(menus.get(i), tempMenus));
            }
        }
        return Sets.newHashSet(sysMenus);
    }

    /**
     * TODO 格式化菜单接口
     *
     * @author Mr.He
     * 2020/3/7 5:01
     * e-mail crabapples.cn@gmail.com
     * qq 294046317
     * pc-name 29404
     */
    @FunctionalInterface
    interface MenusFormat {
        SysMenu format(SysMenu sysMenu, List<SysMenu> tempMenus);
    }
}
