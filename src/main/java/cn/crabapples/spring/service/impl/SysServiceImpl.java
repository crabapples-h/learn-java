package cn.crabapples.spring.service.impl;

import cn.crabapples.spring.common.config.ApplicationConfigure;
import cn.crabapples.spring.common.utils.AesUtils;
import cn.crabapples.spring.dao.SysRepository;
import cn.crabapples.spring.dto.ResponseDTO;
import cn.crabapples.spring.entity.SysMenu;
import cn.crabapples.spring.entity.SysUser;
import cn.crabapples.spring.form.UserForm;
import cn.crabapples.spring.service.SysService;
import cn.crabapples.spring.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
            String password = form.getPassword();
            for (int i = 0; i < 100; i++) {
                password = DigestUtils.md5Hex(password);
            }
            password = AesUtils.doFinal(aesKey, password, Cipher.ENCRYPT_MODE);
            logger.info("开始登录->用户名:[{}],密码:[{}]", username, password);
            shiroCheckLogin(username, password);
            SysUser user = shiroCheckLogin(username, password);
            if (user == null) {
                return ResponseDTO.returnError("用户名或密码错");
            }
            String token = DigestUtils.md5Hex(user.getId() + System.currentTimeMillis()).toUpperCase();
            String tokenKey = redisPrefix + ":loginToken:" + user.getId();
            Map<String, String> tokenInfo = new HashMap<>(2);
            tokenInfo.put("token", token);
            Boolean redisSetStatus = redisTemplate.opsForValue().setIfAbsent(tokenKey, tokenInfo, tokenCacheTime, TimeUnit.MINUTES);
            Long time = redisTemplate.getExpire(tokenKey);
            logger.info("登录成功->redis缓存设置状态:[{}],token:[{}],token过期剩余时间为:[{}]秒,", redisSetStatus, tokenInfo, time);
            return ResponseDTO.returnSuccess("登录成功", tokenInfo);
        } catch (Exception e) {
            logger.warn("登录失败:[{}]", e.getMessage(), e);
            return ResponseDTO.returnError(e.getMessage());
        }
    }

    /**
     * shiro认证
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    private SysUser shiroCheckLogin(String username, String password) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
            SysUser user = (SysUser) subject.getPrincipal();
            subject.getSession().setAttribute("user", user);
            return user;
        } catch (IncorrectCredentialsException e) {
            logger.warn("shiro认证失败,密码错误:[{}]", e.getMessage());
        } catch (UnknownAccountException e) {
            logger.warn("shiro认证失败,用户不存在:[{}]", e.getMessage());
        } catch (Exception e) {
            logger.error("shiro认证失败", e);
        }
        return null;
    }

    //    @Cacheable(value = "crabapples:sysMenus", key = "#auth")
    @Override
    public List<SysMenu> getSysMenus() {
        Subject subject = SecurityUtils.getSubject();
        Object object = subject.getSession().getAttribute("user");
        System.err.println(object);
        List<SysMenu> menus = sysRepository.findByParentIdIsNull();
        insertChildrenMenus(menus);
        menus.forEach(System.err::println);
        return menus;
    }

    private void insertChildrenMenus(List<SysMenu> menus) {
        menus.forEach(e->{
            List<SysMenu> children = sysRepository.findByParentId(e.getId());
            insertChildrenMenus(children);
            e.setChildren(children);
        });
    }
}
