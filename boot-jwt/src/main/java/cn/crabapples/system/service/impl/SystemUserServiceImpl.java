package cn.crabapples.system.service.impl;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.dic.DIC;
import cn.crabapples.common.jwt.JwtConfigure;
import cn.crabapples.common.jwt.JwtTokenUtils;
import cn.crabapples.common.utils.AssertUtils;
import cn.crabapples.system.dao.RolesDAO;
import cn.crabapples.system.dao.UserDAO;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.SystemUserService;
import cn.hutool.crypto.digest.MD5;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * TODO 用户相关服务实现类[用户]
 *
 * @author Mr.He
 * 2020/1/27 2:10
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Service
@Slf4j
public class SystemUserServiceImpl  implements SystemUserService {
    @Value("${isCrypt}")
    private boolean isCrypt;
    private final UserDAO userDAO;
    private final RolesDAO rolesDAO;
    @Value("${isDebug}")
    private boolean isDebug;
    private final JwtConfigure jwtConfigure;
    private final HttpServletRequest request;


    public SystemUserServiceImpl(UserDAO userDAO, RolesDAO rolesDAO, JwtConfigure jwtConfigure,
                                 HttpServletRequest request) {
        this.userDAO = userDAO;
        this.rolesDAO = rolesDAO;
        this.jwtConfigure = jwtConfigure;
        this.request = request;
    }

    @Override
    public SysUser findById(String id) {
        return userDAO.findById(id);
    }

    @Override
    public List<SysUser> findById(List<String> ids) {
        return userDAO.findByIds(ids);
    }

    @Override
    public List<SysUser> findByName(String name) {
        UserForm form = new UserForm();
        form.setName(name);
        return userDAO.findAll(form);
    }

    @Override
    public SysUser findByUsername(String username) {
        UserForm form = new UserForm();
        form.setUsername(username);
        return userDAO.findOne(form);
    }

    @Override
    public List<SysUser> findAll() {
        return userDAO.findAll();
    }

    /**
     * 添加用户
     */
    @Override
    public boolean addUser(UserForm form) {
        SysUser entity = new SysUser();
        BeanUtils.copyProperties(form, entity);
        String password;
        if (isCrypt) {
            password = MD5.create().digestHex(form.getPassword().getBytes(StandardCharsets.UTF_8));
        } else {
            password = form.getPassword();
        }
        entity.setPassword(password);
        entity.setStatus(DIC.USER_LOCK);
//        entity.setRolesList( form.getRolesList()));
        return userDAO.saveOrUpdate(entity);
    }

    /**
     * 删除用户
     */
    @Override
    public boolean delUser(String id) {
        return userDAO.delUser(id);
    }

    /**
     * 编辑用户
     */
    @Override
    public boolean editUser(UserForm form) {
        SysUser entity = userDAO.findById(form.getId());
        BeanUtils.copyProperties(form, entity);
        String password = form.getPassword();
        if (!StringUtils.isEmpty(password)) {
            if (isCrypt) {
                password = MD5.create().digestHex(form.getPassword().getBytes(StandardCharsets.UTF_8));
            }
        }
        entity.setPassword(password);
//        entity.setRolesList(String.join(",", form.getRolesList()));
        return userDAO.saveOrUpdate(entity);
    }

    /**
     * 锁定用户
     */
    @Override
    public boolean lockUser(String id) {
        return userDAO.lockUser(id);
    }

    /**
     * 解锁用户
     */
    @Override
    public boolean unlockUser(String id) {
        return userDAO.unlockUser(id);
    }

    /**
     * 修改密码
     */
    @Override
    public boolean updatePassword(UserForm.ResetPassword form) {
        SysUser user = checkPassword(form);
        String password = form.getOldPassword();
        String newPassword = form.getNewPassword();
        password = MD5.create().digestHex(password.getBytes(StandardCharsets.UTF_8));
        if (user.getPassword().equals(password)) {
            newPassword = MD5.create().digestHex(newPassword.getBytes(StandardCharsets.UTF_8));
            user.setPassword(newPassword);
            return userDAO.saveOrUpdate(user);
        }
        throw new ApplicationException("密码错误");
    }

    /**
     * 重置密码
     */
    @Override
    public boolean resetPassword(UserForm.ResetPassword form) {
        SysUser user = checkPassword(form);
        String newPassword = form.getNewPassword();
        AssertUtils.notNull(user, "用户不存在");
        newPassword = MD5.create().digestHex(newPassword.getBytes(StandardCharsets.UTF_8));
        user.setPassword(newPassword);
        return userDAO.saveOrUpdate(user);
    }

    /**
     * 获取当前登录用户的信息
     */
    @Override
    public SysUser getUserInfo() {
        String userId = "001";
        if (!isDebug) {
            String token = request.getHeader(jwtConfigure.getAuthKey());
            if (StringUtils.isEmpty(token)) {
                throw new ApplicationException("token为空");
            }
            Claims claims = JwtTokenUtils.parseJWT(token, jwtConfigure.getBase64Secret());
            userId = String.valueOf(claims.get("userId"));
        }
//        Object user = cacheUtils.get(userId);
        return userDAO.findById(userId);
    }
    //    /**
//     * 添加或编辑用户时将填充用户拥有的角色信息
//     */
//    private void setRolesList(List<String> ids, SysUser entity) {
//        List<SysRoles> roles = rolesDAO.findByIds(ids);
//        entity.setRolesList(roles);
//    }

    /**
     * 修改或重置密码时检测
     * 1、检测两次输入密码是否相同
     * 2、检测用户是否存在
     */
    private SysUser checkPassword(UserForm.ResetPassword form) {
        String newPassword = form.getNewPassword();
        String againPassword = form.getAgainPassword();
        if (!newPassword.equals(againPassword)) {
            throw new ApplicationException("两次密码不一致");
        }
        SysUser user = userDAO.findById(form.getId());
        AssertUtils.notNull(user, "用户不存在");
        return user;
    }

}
