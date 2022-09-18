package cn.crabapples.system.service;

import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;

import java.util.List;
import java.util.Optional;

/**
 * TODO 用户相关服务
 *
 * @author Mr.He
 * 2020/1/27 2:10
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public interface UserService {
    /**
     * 测试方法-使用hql查询
     */
    List<SysUser> findByHQL(String name);

    /**
     * 测试方法-使用sql查询
     */
    List<SysUser> findBySQL(String name);

    /**
     * 添加用户
     */
    SysUser addUser(UserForm form);

    /**
     * 修改用户
     */
    SysUser editUser(UserForm form);

    /**
     * 删除用户
     */
    void delUser(String id);

    List<SysUser> findByName(String name);

    /**
     * 禁用用户
     */
    void unActiveUser(String id);

    /**
     * 激活用户
     */
    void activeUser(String id);

    Optional<SysUser> findByUsernameAndPasswordAndStatusNotAndDelFlagNot(String username, String password, int status, int delFlag);
}
