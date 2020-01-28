package Mr.He.spring.service;

import Mr.He.spring.entity.User;
import Mr.He.spring.form.UserForm;

/**
 * TODO 系统相关服务
 *
 * @author Mr.He
 * 2020/1/28 23:22
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public interface SysService {
    /**
     * 用户登录
     */
    String login(UserForm form);
}
