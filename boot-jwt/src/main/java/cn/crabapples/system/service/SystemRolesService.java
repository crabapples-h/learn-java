package cn.crabapples.system.service;

import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.dto.SysRolesDTO;
import cn.crabapples.system.entity.SysRoles;
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
public interface SystemRolesService extends BaseService {

    List<SysRolesDTO> getUserRoles();

    List<SysRoles> getByIds(List<String> ids);

    List<SysRoles> getByIds(String[] ids);

    SysRoles getById(String id);

    Iterable<SysRoles> getRolesList(Integer pageIndex, Integer pageSize, RolesForm form);

    SysRoles saveRoles(RolesForm form);

    SysRoles saveRoles(SysRoles e);

    SysRoles removeRoles(String id);

    List<SysRoles> findByMenusId(String id);
}
