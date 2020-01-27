package Mr.He.spring.service.impl;

import Mr.He.spring.common.ApplicationException;
import Mr.He.spring.dao.UserRepository;
import Mr.He.spring.entity.User;
import Mr.He.spring.form.UserForm;
import Mr.He.spring.service.UserService;
import org.springframework.stereotype.Service;

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
    public void delUser(String id) {
        userRepository.delUser(id);
    }
}
