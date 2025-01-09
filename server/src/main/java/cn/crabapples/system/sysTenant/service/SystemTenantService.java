package cn.crabapples.system.sysTenant.service;

import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.sysTenant.entity.SysTenant;
import cn.crabapples.system.sysTenant.form.TenantForm;
import com.mybatisflex.core.paginate.Page;

import java.util.List;

public interface SystemTenantService extends BaseService<SysTenant> {
    Page<SysTenant> page(Integer pageIndex, Integer pageSize, TenantForm form);

    List<SysTenant> list(TenantForm form);

    boolean saveTenant(TenantForm form);

    boolean deleteById(String id);


}
