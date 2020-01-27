package Mr.He.spring.service;

import Mr.He.spring.entity.User;
import Mr.He.spring.form.UserForm;

/**
 * TODO 用户Service接口
 *
 * @author Mr.He
 *  2020/1/27 2:10
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public interface UserService {
    /**
     * 添加用户
     */
    User addUser(UserForm form);
    /**
     * 修改用户
     */
    User editUser(UserForm form);
}