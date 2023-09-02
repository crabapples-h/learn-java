package cn.crabapples.system.service;

import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.dto.SysRolesDTO;
import cn.crabapples.system.entity.SysRole;
import cn.crabapples.system.form.RolesForm;

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

    List<SysRole> getUserRoles();

    List<SysRolesDTO> getUserRolesDTO();

    List<SysRole> getByIds(List<String> ids);

    Iterable<SysRole> getRolesList(Integer pageIndex, Integer pageSize, RolesForm form);

    boolean saveRoles(RolesForm form);

    boolean removeRoles(String id);

    List<SysRole> findByMenusId(String id);

}
