package cn.crabapples.system.service;

import cn.crabapples.common.PageDTO;
import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.dto.SysUserDTO;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
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

    Page<SysUser> findAll(PageDTO page);

    List<SysUserDTO> findAll(HttpServletRequest request, PageDTO page);

    SysUser addUser(UserForm form);

    SysUser delUser(String id);

    SysUser editUser(UserForm form);

    void lockUser(String id);

    void unlockUser(String id);

    void updatePassword(UserForm.ResetPassword form);

    void resetPassword(UserForm.ResetPassword form);

}
