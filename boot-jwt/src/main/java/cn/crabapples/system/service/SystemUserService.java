package cn.crabapples.system.service;

import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;

import java.util.List;


/**
 * TODO 系统相关服务[用户]
 *
 * @author Mr.He
 * 2021/4/25 0:34
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public interface SystemUserService extends BaseService {

    SysUser findById(String id);

    List<SysUser> findById(List<String> ids);

    List<SysUser> findByName(String name);

    SysUser findByUsername(String username);

    List<SysUser> findAll();

    int addUser(UserForm form);

    int delUser(String id);

    int editUser(UserForm form);

    int lockUser(String id);

    int unlockUser(String id);

    int updatePassword(UserForm.ResetPassword form);

    int resetPassword(UserForm.ResetPassword form);

    SysUser getUserInfo();
}
