package cn.crabapples.system.service.impl;

import cn.crabapples.common.config.DictionaryConfigure;
import cn.crabapples.common.utils.AssertUtils;
import cn.crabapples.common.utils.CacheUtils;
import cn.crabapples.common.utils.jwt.JwtConfigure;
import cn.crabapples.common.utils.jwt.JwtTokenUtils;
import cn.crabapples.system.dao.UserDAO;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.UserService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TODO 用户相关服务实现类
 *
 * @author Mr.He
 * 2020/1/27 2:10
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Service
public class UserServiceImpl implements UserService {
    @Value("${isDebug}")
    private boolean isDebug;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDAO userDAO;
    private final CacheUtils cacheUtils;
    private final JwtConfigure jwtConfigure;

    public UserServiceImpl(UserDAO userDAO, CacheUtils cacheUtils, JwtConfigure jwtConfigure) {
        this.userDAO = userDAO;
        this.cacheUtils = cacheUtils;
        this.jwtConfigure = jwtConfigure;
    }

    /**
     * 根据 [用户名] 查询用户
     *
     * @param username 用户名
     * @return 查询到的用户
     */
    @Override
    public SysUser findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public SysUser addUser(UserForm form) {
        return userDAO.save(form);
    }

    @Override
    public SysUser editUser(UserForm form) {
        SysUser user = userDAO.findById(form.getId());
        AssertUtils.notNull(user, "用户不存在");
        return userDAO.save(form);
    }

    @Override
    public void delUser(String id) {
        SysUser user = userDAO.findById(id);
        AssertUtils.notNull(user, "用户不存在");
        userDAO.delUser(id);
    }

    @Override
    public List<SysUser> findByName(String name) {
        return userDAO.findByName(name);
    }

    @Override
    public void changeStatus(String id) {
        SysUser user = userDAO.findById(id);
        AssertUtils.notNull(user, "用户不存在");
        user.setStatus(Math.abs(user.getStatus() - 1));
        userDAO.save(user);
    }

    @Override
    public List<SysUser> findAll() {
        logger.info("开始获取所有用户");
        return userDAO.findAll();
    }

    /**
     * 根据[用户名] [密码] [状态] [删除标记] 查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @param status   状态
     * @param delFlag  删除标记
     * @return 查询到的用户
     */
    @Override
    public SysUser findByUsernameAndPasswordAndStatusNotAndDelFlagNot(String username, String password, int status, int delFlag) {
        return userDAO.findByUsernameAndPasswordAndStatusNotAndDelFlagNot(username, password, status, delFlag);
    }

    @Override
    public SysUser getUserInfo(HttpServletRequest request) {
        String token = JwtTokenUtils.getToken(request, jwtConfigure.getAuthKey());
        Claims claims = JwtTokenUtils.parseJWT(token, jwtConfigure.getBase64Secret());
        String userId = claims.getSubject();
        Object user = cacheUtils.get(userId);
        AssertUtils.notNull(user, DictionaryConfigure.USER_IS_NULL);
        return (SysUser) user;
    }

}
