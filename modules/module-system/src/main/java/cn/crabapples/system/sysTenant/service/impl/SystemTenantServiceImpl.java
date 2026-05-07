package cn.crabapples.system.sysTenant.service.impl;

import cn.crabapples.system.sysTenant.dao.TenantDAO;
import cn.crabapples.system.sysTenant.entity.SysTenant;
import cn.crabapples.system.sysTenant.form.TenantForm;
import cn.crabapples.system.sysTenant.service.SystemTenantService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class SystemTenantServiceImpl implements SystemTenantService {
    private final TenantDAO dictDAO;

    public SystemTenantServiceImpl(TenantDAO dictDAO) {
        this.dictDAO = dictDAO;
    }


    @Override
    public Page<SysTenant> page(Integer pageIndex, Integer pageSize, TenantForm form) {
        return dictDAO.findAll(pageIndex, pageSize, form);
    }

    @Override
    public List<SysTenant> list(TenantForm form) {
        return dictDAO.findAll( form);
    }


    @Override
    public boolean saveTenant(TenantForm form) {
        return dictDAO.saveOrUpdate(form.toEntity());
    }

    @Override
    public boolean deleteById(String id) {
        return dictDAO.removeById(id);
    }
}
