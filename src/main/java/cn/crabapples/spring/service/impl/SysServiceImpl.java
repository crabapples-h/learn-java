package cn.crabapples.spring.service.impl;

import cn.crabapples.spring.common.config.ApplicationConfigure;
import cn.crabapples.spring.dao.SysMenuRepository;
import cn.crabapples.spring.dto.ResponseDTO;
import cn.crabapples.spring.entity.SysMenu;
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

    private final SysMenuRepository sysMenuRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    public SysServiceImpl(ApplicationConfigure applicationConfigure,
                          UserService userService,
                          SysMenuRepository sysMenuRepository,
                          RedisTemplate<String, Object> redisTemplate) {
        this.userService = userService;
        this.sysMenuRepository = sysMenuRepository;
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
     *
     * @return 当前用户拥有的菜单
     */
    //    @Cacheable(value = "crabapples:sysMenus", key = "#auth")
    @Override
    public List<SysMenu> getSysMenus() {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        logger.info("开始获取用户:[{}]已授权的菜单", user.getId());
        Set<SysMenu> userMenus = new HashSet<>();
        user.getSysRoles().forEach(e -> userMenus.addAll(e.getSysMenus()));
//        logger.info("用户:[{}]已授权的菜单为:[{}]即将开始格式化菜单", user.getId(), userMenus);
//        List<SysMenu> allMenus = sysMenuRepository.findAll();

        userMenus.forEach(e -> {
            System.err.println(e.getName() + ":" + e.isShowFlag());
        });
        List<SysMenu> allMenuTreesTemp = insertChildrenMenus(sysMenuRepository.findByParentIdIsNull());
        saveObj(userMenus, "8");
        saveObj(allMenuTreesTemp, "9");
        userMenus.forEach(e -> filterChildrenMenus(e, allMenuTreesTemp));
        userMenus.forEach(e -> filterParentMenus(allMenuTreesTemp));
        changeParentFlag(allMenuTreesTemp);
        System.err.println(allMenuTreesTemp);
//        List<SysMenu> sysMenus = Lists.newArrayList(allMenuTreesTemp);
        List<SysMenu> sysMenus = removeMenus(allMenuTreesTemp);

        System.err.println(allMenuTreesTemp);
        return sysMenus;
    }

    public List<SysMenu> removeMenus(List<SysMenu> sysMenus) {
        for (int i = sysMenus.size() - 1; i >= 0; i--) {
//            List<SysMenu> children = sysMenus.get(i).getChildren();
            sysMenus.get(i).setChildren(removeMenus(sysMenus.get(i).getChildren()));
            if (sysMenus.get(i).getChildren().size() == 0) {
                if (!sysMenus.get(i).isShowFlag()) {
                    sysMenus.remove(sysMenus.get(i));
                }
            }
//            sysMenus.get(i).setChildren(sysMenus.get(i).getChildren());
        }
        return sysMenus;
    }

    public void changeParentFlag(List<SysMenu> allMenus) {
        allMenus.forEach(e -> {
//            System.err.println(e);

        });
    }

    public static void filterChildrenMenus(SysMenu userMenu, List<SysMenu> allMenu) {
        allMenu.forEach(e -> {
            filterChildrenMenus(userMenu, e.getChildren());
            if (userMenu.getId().equals(e.getId())) {
                e.setShowFlag(userMenu.getId().equals(e.getId()));
            }
        });
    }

    public static void filterParentMenus(List<SysMenu> allMenu) {
        allMenu.forEach(e -> {
            filterParentMenus(e.getChildren());
            e.getChildren().forEach(r -> {
                if (r.isShowFlag()) {
                    System.err.println(e.getName() + "" + e.isShowFlag());
                    e.setShowFlag(true);
                }
            });
        });
    }


    public List<SysMenu> insertChildrenMenus(List<SysMenu> menus) {
        menus.forEach(e -> {
            List<SysMenu> children = sysMenuRepository.findByParentId(e.getId());
            insertChildrenMenus(children);
            e.setChildren(children);
        });
        return menus;
    }

    void saveObj(Object obj, String f) {
        File file = new File("d:/" + f);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(obj);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Object readObj(String f) {
        File file = new File("d:/" + f);
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("all")
    public static void main(String[] args) {
        Set<SysMenu> userMenus = (Set<SysMenu>) readObj("8");
        List<SysMenu> allMenuTreesTemp = (List<SysMenu>) readObj("9");
        userMenus.forEach(e -> filterChildrenMenus(e, allMenuTreesTemp));
        userMenus.forEach(e -> filterParentMenus(allMenuTreesTemp));
        System.err.println(userMenus);
        System.err.println(allMenuTreesTemp);
//        changeParentFlag(allMenuTreesTemp);
//        System.err.println(allMenuTreesTemp);
////        List<SysMenu> sysMenus = Lists.newArrayList(allMenuTreesTemp);
//        List<SysMenu> sysMenus = removeMenus(allMenuTreesTemp);

//        System.err.println(allMenuTreesTemp);
    }

//    private static SysMenu getParent(SysMenu sysMenu, List<SysMenu> allMenus) {
//        SysMenu parent = new SysMenu();
//        allMenus.forEach(e -> {
//            if (sysMenu.getParentId() != null && sysMenu.getParentId().equals(e.getId())) {
//                getParent(e, allMenus);
//                parent.add(e);
//            }
//        });
//        return parent;
//    }
//
//
//
//    private static Set<SysMenu> getChildren(SysMenu sysMenu, List<SysMenu> allMenus) {
//        Set<SysMenu> children = new HashSet<>();
//        allMenus.forEach(e -> {
//            if (sysMenu.getId().equals(e.getParentId())) {
//                getChildren(e, allMenus);
//                children.add(e);
//            }
//        });
//        return children;
//    }
}
