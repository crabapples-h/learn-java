package cn.crabapples.system.sysUser.service;

import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.dto.SysUserDTO;
import cn.crabapples.system.sysUser.entity.SysUser;
import cn.crabapples.system.sysUser.form.ResetPasswordForm;
import cn.crabapples.system.sysUser.form.UpdatePasswordForm;
import cn.crabapples.system.sysUser.form.UserForm;
import com.mybatisflex.core.paginate.Page;

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

    List<SysUser> findByIds(List<String> ids);

    List<SysUserDTO> findByName(String name);

    SysUser findByUsername(String username);

    Page<SysUserDTO> findAll(Integer pageIndex, Integer pageSize, UserForm form);

    List<SysUserDTO> findAll(UserForm form);

    boolean removeUser(String id);

    boolean lockUser(String id);

    boolean unlockUser(String id);

    boolean updatePassword(UpdatePasswordForm form);

    boolean resetPassword(ResetPasswordForm form);

    SysUser getUserInfo();

    boolean saveUser(UserForm form);

    SysUserDTO getById(String id);
}
