package cn.crabapples.system.service.impl;

import cn.crabapples.common.utils.AssertUtils;
import cn.crabapples.system.dao.UserDAO;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
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
    public SysUser getUserInfo() {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        String username;
        if (user == null) {
            if (isDebug) {
                username = "admin";
            } else {
                throw new UnauthenticatedException("用户尚未登录");
            }
        } else {
            username = user.getUsername();
        }
        return userDAO.findByUsername(username);
    }

}
