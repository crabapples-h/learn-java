package cn.crabapples.system.service.impl;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.DIC;
import cn.crabapples.common.PageDTO;
import cn.crabapples.common.utils.AssertUtils;
import cn.crabapples.common.utils.jwt.JwtConfigure;
import cn.crabapples.common.utils.jwt.JwtTokenUtils;
import cn.crabapples.system.dao.RolesDAO;
import cn.crabapples.system.dao.UserDAO;
import cn.crabapples.system.dto.SysUserDTO;
import cn.crabapples.system.entity.SysRoles;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.SysUserService;
import cn.hutool.crypto.digest.MD5;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
public class SysUserServiceImpl implements SysUserService {
    @Value("${isDebug}")
    private boolean isDebug;
    private final UserDAO userDAO;
    private final RolesDAO rolesDAO;
    private final JwtConfigure jwtConfigure;

    public SysUserServiceImpl(UserDAO userDAO, RolesDAO rolesDAO, JwtConfigure jwtConfigure) {
        this.userDAO = userDAO;
        this.rolesDAO = rolesDAO;
        this.jwtConfigure = jwtConfigure;
    }

    /**
     * 获取当前登录用户的信息
     */
    public SysUser getUserInfo(HttpServletRequest request) {
        String userId = "001";
        if (!isDebug) {
            final String authHeader = request.getHeader(jwtConfigure.getAuthKey());
            Claims claims = JwtTokenUtils.parseJWT(authHeader, jwtConfigure.getBase64Secret());
            userId = String.valueOf(claims.get("userId"));
        }
        System.err.println(userDAO.findById(userId));
        return userDAO.findById(userId);
    }


    /**
     * 根据用户名查找用户
     */
    @Override
    public SysUser findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    /**
     * 添加用户
     */
    @Override
    public SysUser addUser(UserForm form) {
        SysUser entity = new SysUser();
        BeanUtils.copyProperties(form, entity);
        setRolesList(form.getRolesList(), entity);
        return userDAO.save(entity);
    }

    /**
     * 编辑用户
     */
    @Override
    public SysUser editUser(UserForm form) {
        SysUser entity = userDAO.findById(form.getId());
        String password = entity.getPassword();
        BeanUtils.copyProperties(form, entity);
        entity.setPassword(password);
        setRolesList(form.getRolesList(), entity);
        return userDAO.save(entity);
    }

    /**
     * 添加或编辑用户时将填充用户拥有的角色信息
     */
    private void setRolesList(List<String> ids, SysUser entity) {
        List<SysRoles> roles = rolesDAO.findByIds(ids);
        entity.setRolesList(roles);
    }

    /**
     * 删除用户
     */
    @Override
    public SysUser delUser(String id) {
        SysUser user = userDAO.findById(id);
        user.setDelFlag(DIC.IS_DEL);
        return userDAO.save(user);
    }

    /**
     * 获取用户列表[全部]
     */
    @Override
    public List<SysUser> findAll() {
        return userDAO.findAll();
    }

    /**
     * 获取用户列表[分页]
     */
    @Override
    public Page<SysUser> findAll(PageDTO page) {
        return userDAO.findAll(page);
    }

    /**
     * 获取用户列表[分页](返回脱敏后的用户列表)
     */
    @Override
    public List<SysUserDTO> getUserPage(HttpServletRequest request, PageDTO page) {
        Page<SysUser> userPage = userDAO.findAll(page);
        Pageable pageable = userPage.getPageable();
        page.setDataCount(userDAO.count());
        page.setPageIndex(pageable.getPageNumber());
        return userDAO.findAll(page).stream().map(e -> e.toDTO(new SysUserDTO())).collect(Collectors.toList());
    }

    /**
     * 锁定用户
     */
    @Override
    public SysUser lockUser(String id) {
        SysUser user = userDAO.findById(id);
        user.setStatus(DIC.USER_LOCK);
        return userDAO.save(user);
    }

    /**
     * 解锁用户
     */
    @Override
    public SysUser unlockUser(String id) {
        SysUser user = userDAO.findById(id);
        user.setStatus(DIC.USER_UNLOCK);
        return userDAO.save(user);
    }

    /**
     * 修改密码
     */
    @Override
    public SysUser updatePassword(UserForm form) {
        SysUser user = checkPassword(form);
        String password = form.getPassword();
        String newPassword = form.getNewPassword();
        password = MD5.create().digestHex(password.getBytes(StandardCharsets.UTF_8));
        if (user.getPassword().equals(password)) {
            newPassword = MD5.create().digestHex(newPassword.getBytes(StandardCharsets.UTF_8));
            user.setPassword(newPassword);
            return userDAO.save(user);
        }
        throw new ApplicationException("密码错误");
    }

    /**
     * 重置密码
     */
    @Override
    public SysUser resetPassword(UserForm form) {
        SysUser user = checkPassword(form);
        String newPassword = form.getNewPassword();
        AssertUtils.notNull(user, "用户不存在");
        newPassword = MD5.create().digestHex(newPassword.getBytes(StandardCharsets.UTF_8));
        user.setPassword(newPassword);
        return userDAO.save(user);
    }

    /**
     * 修改或重置密码时检测
     * 1、检测两次输入密码是否相同
     * 1、检测用户是否存在
     */
    private SysUser checkPassword(UserForm form) {
        String newPassword = form.getNewPassword();
        String againPassword = form.getAgainPassword();
        if (!newPassword.equals(againPassword)) {
            throw new ApplicationException("两次密码不一致");
        }
        SysUser user = userDAO.findById(form.getId());
        AssertUtils.notNull(user, "用户不存在");
        return user;
    }

    @Override
    public SysUser findById(String id) {
        throw new ApplicationException("暂未实现");
//        return userDAO.findById(id);
    }

    @Override
    public List<SysUser> findByIds(List<String> ids) {
        throw new ApplicationException("暂未实现");
//        return userDAO.findByIds(ids);
    }


    @Override
    public List<SysUser> findByName(String name) {
        throw new ApplicationException("暂未实现");
//        return userDAO.findByName(name);
    }

    @Override
    public List<SysUser> findByNameLike(String name) {
        throw new ApplicationException("暂未实现");
//        return userDAO.findByNameLike(name);
    }
}
