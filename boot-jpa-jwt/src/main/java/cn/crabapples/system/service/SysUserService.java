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
public interface SysUserService extends BaseService {

    SysUser findByUsername(String username);

    long countByUsername(String username);


    SysUser addUser(UserForm form);

    SysUser editUser(UserForm form);

    SysUser delUser(String id);

    SysUser lockUser(String id);

    SysUser unlockUser(String id);

    List<SysUser> findAll();

    Page<SysUser> findAll(PageDTO page);

    List<SysUserDTO> getUserPage(HttpServletRequest request, PageDTO page);

    SysUser updatePassword(UserForm form);

    SysUser resetPassword(UserForm form);

    SysUser getUserInfo(HttpServletRequest request);

    SysUser findById(String id);

    List<SysUser> findByIds(List<String> ids);

    List<SysUser> findByName(String name);

    List<SysUser> findByNameLike(String name);

}
