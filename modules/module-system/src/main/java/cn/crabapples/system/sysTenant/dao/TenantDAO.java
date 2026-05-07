package cn.crabapples.system.sysTenant.dao;

import cn.crabapples.system.sysTenant.dao.mybatis.mapper.TenantMapper;
import cn.crabapples.system.sysTenant.entity.SysTenant;
import cn.crabapples.system.sysTenant.form.TenantForm;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TenantDAO extends ServiceImpl<TenantMapper, SysTenant> {
    private LambdaQueryWrapper<SysTenant> createQueryWrapper(SysTenant entity) {
        return new LambdaQueryWrapper<>(entity)
                .like(!StringUtils.isEmpty(entity.getName()), SysTenant::getName, entity.getName());
    }

    public Page<SysTenant> findAll(Integer pageIndex, Integer pageSize, TenantForm form) {
        return baseMapper.selectPage(Page.of(pageIndex, pageSize), createQueryWrapper(form.toEntity()));
    }

    public List<SysTenant> findAll(TenantForm form) {
        return baseMapper.selectList(createQueryWrapper(form.toEntity()));
    }
}
