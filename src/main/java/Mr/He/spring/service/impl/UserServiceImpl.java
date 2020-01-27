package Mr.He.spring.service.impl;

import Mr.He.spring.dao.UserRepository;
import Mr.He.spring.entity.User;
import Mr.He.spring.service.UserService;
import org.springframework.stereotype.Service;

/**
 * TODO 用户Service实现
 *
 * @author Mr.He
 *  2020/1/27 2:10
 * e-mail wishforyou.xia@gmail.com
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
    public User addUser(User user) {
        return userRepository.save(user);
    }
}
