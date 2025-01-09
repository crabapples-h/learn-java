package cn.crabapples.system.sysTenant.dao;

import cn.crabapples.system.sysTenant.dao.mybatis.mapper.TenantMapper;
import cn.crabapples.system.sysTenant.entity.SysTenant;
import cn.crabapples.system.sysTenant.form.TenantForm;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TenantDAO extends ServiceImpl<TenantMapper, SysTenant> {
    public Page<SysTenant> findAll(Integer pageIndex, Integer pageSize, TenantForm form) {
        return mapper.paginate(Page.of(pageIndex, pageSize), QueryWrapper.create(form.toEntity()));
    }

    public List<SysTenant> findAll(TenantForm form) {
        return mapper.selectListByQuery(QueryWrapper.create(form.toEntity()));
    }
}
