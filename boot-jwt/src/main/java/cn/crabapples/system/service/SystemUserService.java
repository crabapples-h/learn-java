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

    boolean addUser(UserForm form);

    boolean delUser(String id);

    boolean editUser(UserForm form);

    boolean lockUser(String id);

    boolean unlockUser(String id);

    boolean updatePassword(UserForm.ResetPassword form);

    boolean resetPassword(UserForm.ResetPassword form);

    SysUser getUserInfo();
}
