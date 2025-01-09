package cn.crabapples.system.sysDepart.service;

import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.sysDepart.entity.SysDepart;
import cn.crabapples.system.sysDepart.form.DepartForm;
import com.mybatisflex.core.paginate.Page;

import java.util.List;

public interface SystemDepartService extends BaseService<SysDepart> {
    Page<SysDepart> page(Integer pageIndex, Integer pageSize, DepartForm form);

    List<SysDepart> list(DepartForm form);

    boolean saveDepart(DepartForm form);

    boolean deleteById(String id);


}
