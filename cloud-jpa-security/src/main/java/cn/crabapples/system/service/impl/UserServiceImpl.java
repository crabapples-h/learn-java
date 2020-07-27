package cn.crabapples.system.service.impl;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.system.dao.UserRepository;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import cn.crabapples.system.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SysUser addUser(UserForm form) {
        return userRepository.save(form.toEntity());
    }

    @Override
    public SysUser editUser(UserForm form) {
        SysUser user = userRepository.findById(form.getId()).orElse(null);
        if (user != null) {
            return userRepository.save(form.toEntity());
        }
        throw new ApplicationException("用户不存在");
    }

    @Override
    @Transactional
    public void delUser(String id) {
        SysUser user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ApplicationException("用户不存在");
        }
        userRepository.delUser(id);
    }

    @Override
    public List<SysUser> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<SysUser> findByHQL(String name) {
        return userRepository.findByHQL(name);
    }

    @Override
    public List<SysUser> findBySQL(String name) {
        return userRepository.findBySQL(name);
    }

    @Override
    @Transactional
    public void unActiveUser(String id) {
        SysUser user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ApplicationException("用户不存在");
        }
        userRepository.unActiveUser(id);
    }

    @Override
    @Transactional
    public void activeUser(String id) {
        SysUser user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ApplicationException("用户不存在");
        }
        userRepository.activeUser(id);
    }

    @Override
    public Optional<SysUser> findByUsernameAndPasswordAndStatusNotAndDelFlagNot(String username, String password, int status, int delFlag) {
        return userRepository.findByUsernameAndPasswordAndStatusNotAndDelFlagNot(username, password, status, delFlag);
    }
}
