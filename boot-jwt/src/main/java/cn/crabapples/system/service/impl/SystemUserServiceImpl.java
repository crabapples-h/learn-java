package cn.crabapples.system.service.impl;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.DIC;
import cn.crabapples.common.PageDTO;
import cn.crabapples.common.utils.AssertUtils;
import cn.crabapples.system.dao.RolesDAO;
import cn.crabapples.system.dao.UserDAO;
import cn.crabapples.system.dto.SysUserDTO;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.SystemUserService;
import cn.hutool.crypto.digest.MD5;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

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
    private final RolesDAO rolesDAO;

    public SystemUserServiceImpl(UserDAO userDAO, RolesDAO rolesDAO) {
        this.userDAO = userDAO;
        this.rolesDAO = rolesDAO;
    }

    @Override
    public SysUser findById(String id) {
        return userDAO.findById(id);
    }

    @Override
    public List<SysUser> findById(List<String> ids) {
        return userDAO.findById(ids);
    }

    @Override
    public List<SysUser> findByName(String name) {
        return userDAO.findByName(name);
    }

    @Override
    public SysUser findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    /**
     * 获取用户列表[全部]
     */
    @Override
    public List<SysUser> findAll() {
        return userDAO.findAll(DIC.NOT_DEL);
    }

    /**
     * 获取用户列表[分页]
     */
    @Override
    public Page<SysUser> findAll(PageDTO page) {
        return userDAO.findAll(page, DIC.NOT_DEL);
    }

    /**
     * 获取用户列表[分页](返回脱敏后的用户列表)
     */
    @Override
    public List<SysUserDTO> findAll(HttpServletRequest request, PageDTO page) {
        Page<SysUser> userPage = userDAO.findAll(page, DIC.NOT_DEL);
        Pageable pageable = userPage.getPageable();
        page.setDataCount(userDAO.count());
        page.setPageIndex(pageable.getPageNumber());
        return userDAO.findAll(page, DIC.NOT_DEL).stream().map(e -> e.toDTO(new SysUserDTO())).collect(Collectors.toList());
    }


    /**
     * 添加用户
     */
    @Override
    public SysUser addUser(UserForm form) {
        SysUser entity = new SysUser();
        BeanUtils.copyProperties(form, entity);
        String password;
        if (isCrypt) {
            password = MD5.create().digestHex(form.getPassword().getBytes(StandardCharsets.UTF_8));
        } else {
            password = form.getPassword();
        }
        entity.setPassword(password);
        entity.setDelFlag(DIC.NOT_DEL);
        entity.setStatus(DIC.USER_LOCK);
//        entity.setRolesList( form.getRolesList()));
        return userDAO.save(entity);
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
     * 编辑用户
     */
    @Override
    public SysUser editUser(UserForm form) {
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
        return userDAO.save(entity);
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
