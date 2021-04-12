package cn.crabapples.system.service.impl;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.DIC;
import cn.crabapples.common.PageDTO;
import cn.crabapples.common.utils.AssertUtils;
import cn.crabapples.common.utils.jwt.JwtConfigure;
import cn.crabapples.common.utils.jwt.JwtTokenUtils;
import cn.crabapples.system.dao.MenusDAO;
import cn.crabapples.system.entity.SysMenus;
import cn.crabapples.system.entity.SysRole;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.MenusForm;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.SysService;
import cn.crabapples.system.service.UserService;
import cn.hutool.crypto.digest.MD5;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
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
    private final UserService userService;
    private final MenusDAO menusDAO;
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtConfigure jwtConfigure;

    public SysServiceImpl(UserService userService,
                          MenusDAO menusDAO,
                          RedisTemplate<String, Object> redisTemplate,
                          JwtConfigure jwtConfigure) {
        this.userService = userService;
        this.menusDAO = menusDAO;
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
     * 获取当前用户拥有的菜单
     *
     * @return 当前用户拥有的菜单
     */
    //    @Cacheable(value = "crabapples:sysMenus", key = "#auth")
    @Override
    public List<SysMenus> getUserMenus(HttpServletRequest request) {
        SysUser user = userService.getUserInfo(request);
        List<SysRole> roles = user.getSysRoles();
        System.err.println(roles);
//        List<SysMenus> menus = menusDAO.findRoot();

//        Subject subject = SecurityUtils.getSubject();
//        Object object = subject.getSession().getAttribute("user");
//        System.err.println(object);
//        List<SysMenus> menus = menusDAO.findRoot();
//        insertChildrenMenus(menus);
//        menus.forEach(System.err::println);
//        return menus;
        throw new ApplicationException("暂未实现");
    }

    @Override
    public SysMenus removeMenus(String id) {
        SysMenus entity = menusDAO.findById(id);
        entity.setDelFlag(DIC.IS_DEL);
        return menusDAO.save(entity);
    }

    @Override
    public List<SysMenus> getMenusList(HttpServletRequest request, PageDTO page) {
        Page<SysMenus> menusPage = menusDAO.findRoot(page);
        Pageable pageable = menusPage.getPageable();
        page.setDataCount(menusDAO.count());
        page.setPageIndex(pageable.getPageNumber());
        return menusPage.stream().collect(Collectors.toList());
    }

    @Override
    public SysMenus saveMenus(MenusForm form) {
        String id = String.valueOf(form.getId());
        log.info("id:[{}][{}]", id, StringUtils.isBlank(id));
        if (StringUtils.isBlank(id)) {
            return addMenus(form);
        } else {
            return editMenus(id, form);
        }
    }

    private SysMenus editMenus(String id, MenusForm form) {
        SysMenus entity = menusDAO.findById(id);
        BeanUtils.copyProperties(form, entity);
        log.info("修改菜单:[{}]", entity);
        return menusDAO.save(entity);
    }

    private SysMenus addMenus(MenusForm form) {
        SysMenus entity = new SysMenus();
        BeanUtils.copyProperties(form, entity);
        log.info("添加新菜单:[{}]", form);
        String parentId = form.getParentId();
        log.info("parentId:[{}][{}]", parentId, StringUtils.isBlank(parentId));
        if (!StringUtils.isBlank(parentId)) {
            return addChildrenMenus(parentId, entity);
        } else {
            return menusDAO.save(entity);
        }
    }

    private SysMenus addChildrenMenus(String prentId, SysMenus entity) {
        log.info("添加新子菜单,parentId:[{}],[{}]", prentId, entity);
        entity.setIsRoot(DIC.NOT_ROOT);
        entity = menusDAO.save(entity);
        SysMenus root = menusDAO.findById(prentId);
        List<SysMenus> children = root.getChildren();
        children.add(entity);
        root.setChildren(children);
        return menusDAO.save(root);
    }

    //    @Override
//    public List<SysMenu> getSysMenus(SysUser user) {
//        logger.info("开始获取用户:[{}]已授权的菜单", user.getId());
//        List<SysMenu> userMenus = new ArrayList<>();
//        user.getSysRoles().forEach(e -> userMenus.addAll(e.getSysMenus()));
//        logger.debug("用户:[{}]已授权的菜单为:[{}]即将开始格式化菜单", user.getId(), userMenus);
//        List<SysMenu> allMenuTreesTemp = insertChildrenMenus(sysMenuRepository.findByParentIdIsNull());
//        userMenus.forEach(e -> filterChildrenMenus(e, allMenuTreesTemp));
//        userMenus.forEach(e -> filterParentMenus(allMenuTreesTemp));
//        logger.debug("用户:[{}]菜单格式化完毕:[{}]", user.getId(), allMenuTreesTemp);
//        logger.debug("开始过滤用户:[{}]没有权限的菜单:[{}]", user.getId(), allMenuTreesTemp);
//        List<SysMenu> sysMenus = removeMenus(allMenuTreesTemp);
//        logger.debug("用户:[{}]没有权限的菜单过滤完毕:[{}]", user.getId(), allMenuTreesTemp);
//        return sysMenus;
//    }

    /**
     * 移除菜单树中显示标识为false的菜单
     *
     * @param sysMenus 数据库中的菜单列表(树状格式)
     * @return 移除后的菜单树
     */
    private List<SysMenus> removeMenus(List<SysMenus> sysMenus) {
        for (int i = sysMenus.size() - 1; i >= 0; i--) {
            sysMenus.get(i).setChildren(removeMenus(sysMenus.get(i).getChildren()));
            if (sysMenus.get(i).getChildren().size() == 0) {
                if (!sysMenus.get(i).isShowFlag()) {
                    sysMenus.remove(sysMenus.get(i));
                }
            }
        }
        return sysMenus;
    }

    /**
     * 将用户菜单中的对应下级显示标识改为true
     *
     * @param allMenu 数据库中的菜单列表(树状格式)
     */
    private static void filterChildrenMenus(SysMenus userMenu, List<SysMenus> allMenu) {
        allMenu.forEach(e -> {
            filterChildrenMenus(userMenu, e.getChildren());
            if (userMenu.getId().equals(e.getId())) {
                e.setShowFlag(userMenu.getId().equals(e.getId()));
            }
        });
    }

    /**
     * 将用户菜单中的对应上级显示标识改为true
     *
     * @param allMenu 数据库中的菜单列表(树状格式)
     */
    private static void filterParentMenus(List<SysMenus> allMenu) {
        allMenu.forEach(e -> {
            filterParentMenus(e.getChildren());
            e.getChildren().forEach(r -> {
                if (r.isShowFlag()) {
                    e.setShowFlag(true);
                }
            });
        });
    }

    /**
     * 格式化菜单树
     *
     * @param menus 上级菜单列表(普通列表)
     * @return 加入下一级后的菜单列表
     */
    private List<SysMenus> insertChildrenMenus(List<SysMenus> menus) {
        menus.forEach(e -> {
            List<SysMenus> children = menusDAO.findByParentId(e.getId());
            insertChildrenMenus(children);
            e.setChildren(children);
        });
        return menus;
    }
}
