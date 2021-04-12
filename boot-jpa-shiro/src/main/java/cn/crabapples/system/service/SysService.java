package cn.crabapples.system.service;

import cn.crabapples.common.PageDTO;
import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.dto.SysRolesDTO;
import cn.crabapples.system.entity.SysMenus;
import cn.crabapples.system.entity.SysRoles;
import cn.crabapples.system.form.MenusForm;
import cn.crabapples.system.form.RolesForm;
import cn.crabapples.system.form.UserForm;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TODO 系统相关服务
 *
 * @author Mr.He
 * 2020/1/28 23:22
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public interface SysService extends BaseService {
    String loginCheck(UserForm form);


    List<SysMenus> getMenusList(HttpServletRequest request, PageDTO page);

    SysMenus saveMenus(MenusForm form);

    List<SysMenus> getUserMenus(HttpServletRequest request);

    SysMenus removeMenus(String id);

    List<SysRolesDTO> getUserRolesList(HttpServletRequest request);

    List<SysRoles> getRolesList(HttpServletRequest request, PageDTO page);

    SysRoles saveRoles(RolesForm form);

    SysRoles removeRoles(String id);
}
