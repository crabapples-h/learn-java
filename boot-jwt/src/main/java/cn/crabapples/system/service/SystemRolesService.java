package cn.crabapples.system.service;

import cn.crabapples.common.PageDTO;
import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.entity.SysRole;
import cn.crabapples.system.form.RolesForm;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * TODO 系统相关服务[角色]
 *
 * @author Mr.He
 * 2021/4/25 0:34
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public interface SystemRolesService extends BaseService<SysRole> {

    List<SysRole> getRolesList(HttpServletRequest request);

    List<SysRole> getByIds(List<String> ids);

    List<SysRole> getByIds(String[] ids);

    SysRole getById(String id);

    List<SysRole> getRolesPage(HttpServletRequest request, PageDTO page);

    SysRole saveRoles(RolesForm form);

    SysRole saveRoles(SysRole entity);

    SysRole removeRoles(String id);

    List<SysRole> findByMenusId(String id);

    List<SysRole> getByUser();
}
