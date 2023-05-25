package cn.crabapples.system.service.impl;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.DIC;
import cn.crabapples.common.utils.AssertUtils;
import cn.crabapples.system.dao.RoleDAO;
import cn.crabapples.system.dao.UserDAO;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.SystemUserService;
import cn.hutool.crypto.digest.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
public class SystemUserServiceImpl implements SystemUserService {
    @Value("${isCrypt}")
    private boolean isCrypt;
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;

    public SystemUserServiceImpl(UserDAO userDAO, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public SysUser findById(String id) {
        return userDAO.findById(id);
    }

    @Override
    public List<SysUser> findByIds(List<String> ids) {
        return userDAO.findByIds(ids);
    }
    /**
     * 获取用户列表[全部]
     */
    @Override
    public SysUser findOne(UserForm form) {
        return userDAO.findOne(form);
    }
    /**
     * 获取用户列表[全部]
     */
    @Override
    public List<SysUser> findAll() {
        return userDAO.getList(null);
    }

    @Override
    public List<SysUser> getList(UserForm form) {
        return userDAO.getList(form);
    }

    @Override
    public Page<SysUser> getPage(UserForm form) {
        PageRequest page = getJpaPage(form);
       return userDAO.getList(form, page);
    }


    /**
     * 添加用户
     */
    @Override
    public void save(UserForm form) {
        SysUser entity = form.toEntity();
        String password;
        if (isCrypt) {
            password = MD5.create().digestHex(form.getPassword().getBytes(StandardCharsets.UTF_8));
        } else {
            password = form.getPassword();
        }
        entity.setPassword(password);
        entity.setDelFlag(DIC.NOT_DEL);
        userDAO.save(entity);
    }


    /**
     * 删除用户
     */
    @Override
    @Transactional
    public void delUser(String id) {
        userDAO.delUser(id);
    }


    /**
     * 锁定用户
     */
    @Override
    @Transactional
    public void lockUser(String id) {
        userDAO.lockUser(id);
    }

    /**
     * 解锁用户
     */
    @Override
    @Transactional
    public void unlockUser(String id) {
        userDAO.unlockUser(id);
    }

    /**
     * 修改密码
     */
    @Override
    public void updatePassword(UserForm.ResetPassword form) {
        SysUser user = checkPassword(form);
        String password = form.getOldPassword();
        String newPassword = form.getNewPassword();
        password = MD5.create().digestHex(password.getBytes(StandardCharsets.UTF_8));
        if (user.getPassword().equals(password)) {
            newPassword = MD5.create().digestHex(newPassword.getBytes(StandardCharsets.UTF_8));
            user.setPassword(newPassword);
            userDAO.save(user);
        }
        throw new ApplicationException("密码错误");
    }

    /**
     * 重置密码
     */
    @Override
    public void resetPassword(UserForm.ResetPassword form) {
        SysUser user = checkPassword(form);
        String newPassword = form.getNewPassword();
        AssertUtils.notNull(user, "用户不存在");
        newPassword = MD5.create().digestHex(newPassword.getBytes(StandardCharsets.UTF_8));
        user.setPassword(newPassword);
        userDAO.save(user);
    }

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
