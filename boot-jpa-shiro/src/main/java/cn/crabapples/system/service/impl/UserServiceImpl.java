package cn.crabapples.system.service.impl;

import cn.crabapples.common.DIC;
import cn.crabapples.common.utils.jwt.JwtConfigure;
import cn.crabapples.common.utils.jwt.JwtTokenUtils;
import cn.crabapples.system.dao.UserDAO;
import cn.crabapples.system.dto.SysUserDTO;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
@Slf4j
public class UserServiceImpl implements UserService {
    @Value("${isDebug}")
    private boolean isDebug;
    private final UserDAO userDAO;
    private final JwtConfigure jwtConfigure;

    public UserServiceImpl(UserDAO userDAO, JwtConfigure jwtConfigure) {
        this.userDAO = userDAO;
        this.jwtConfigure = jwtConfigure;
    }

    @Override
    public SysUser findById(String id) {
        return userDAO.findById(id);
    }

    @Override
    public List<SysUser> findByIds(List<String> ids) {
        return userDAO.findByIds(ids);
    }

    @Override
    public SysUser findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public SysUser addUser(UserForm form) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(form, user);
        return userDAO.save(user);
    }

    @Override
    public SysUser editUser(UserForm form) {
        SysUser user = userDAO.findById(form.getId());
        BeanUtils.copyProperties(form, user);
        return userDAO.save(user);
    }

    @Override
    public SysUser delUser(String id) {
        SysUser user = userDAO.findById(id);
        user.setDelFlag(DIC.IS_DEL);
        return userDAO.save(user);
    }

    @Override
    public List<SysUser> findByName(String name) {
        return userDAO.findByName(name);
    }

    @Override
    public List<SysUser> findByNameLike(String name) {
        return userDAO.findByNameLike(name);
    }

    @Override
    public List<SysUser> findAll() {
        return userDAO.findAll();
    }

    @Override
    public SysUser lockUser(String id) {
        SysUser user = userDAO.findById(id);
        SysUserDTO dto = user.toDTO(new SysUserDTO());
        System.err.println(dto);
//        user.setStatus(DIC.USER_LOCK);
        return userDAO.save(user);
    }

    @Override
    public SysUser unlockUser(String id) {
        SysUser user = userDAO.findById(id);
        user.setStatus(DIC.USER_UNLOCK);
        return userDAO.save(user);
    }

    @Override
    public SysUser getUserInfo(HttpServletRequest request) {
        return getUserInfo(request, jwtConfigure, userDAO, isDebug);
    }


    private SysUser getUserInfo(HttpServletRequest request, JwtConfigure configure, UserDAO userDAO, boolean isDebug) {
        String userId = "001";
        if (!isDebug) {
            final String authHeader = request.getHeader(configure.getAuthKey());
            Claims claims = JwtTokenUtils.parseJWT(authHeader, configure.getBase64Secret());
            userId = String.valueOf(claims.get("userId"));
        }
        System.err.println(userDAO.findById(userId));
        return userDAO.findById(userId);
    }
}
