package cn.crabapples.system.service;

import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * TODO 用户相关服务
 *
 * @author Mr.He
 * 2020/1/27 2:10
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public interface UserService extends BaseService {

    SysUser findById(String id);

    List<SysUser> findByIds(List<String> ids);

    SysUser findByUsername(String username);

    SysUser addUser(UserForm form);

    SysUser editUser(UserForm form);

    SysUser delUser(String id);

    List<SysUser> findByName(String name);

    List<SysUser> findByNameLike(String name);

    SysUser lockUser(String id);

    SysUser unlockUser(String id);

    List<SysUser> findAll();

    SysUser getUserInfo(HttpServletRequest request);
}
