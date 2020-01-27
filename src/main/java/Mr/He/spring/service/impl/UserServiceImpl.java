package Mr.He.spring.service.impl;

import Mr.He.spring.common.ApplicationException;
import Mr.He.spring.dao.UserRepository;
import Mr.He.spring.entity.User;
import Mr.He.spring.form.UserForm;
import Mr.He.spring.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * TODO 用户Service实现
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
    public User addUser(UserForm form) {
        return userRepository.save(form.toEntity());
    }

    @Override
    public User editUser(UserForm form) {
        User user = userRepository.findById(form.getId()).orElse(null);
        if(user != null){
            return userRepository.save(form.toEntity());
        }
        throw new ApplicationException("用户不存在");
    }

    @Override
    @Transactional
    public void delUser(String id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new ApplicationException("用户不存在");
        }
        userRepository.delUser(id);
    }

    @Override
    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<User> findByHQL(String name) {
        return userRepository.findByHQL(name);
    }
}
