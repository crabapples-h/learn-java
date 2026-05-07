package cn.crabapples.system.sysDepart.dao;

import cn.crabapples.system.sysDepart.dao.mybatis.mapper.DepartMapper;
import cn.crabapples.system.sysDepart.entity.SysDepart;
import cn.crabapples.system.sysDepart.form.DepartForm;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartDAO extends ServiceImpl<DepartMapper, SysDepart> {
    private LambdaQueryWrapper<SysDepart> createQueryWrapper(SysDepart entity) {
        return new LambdaQueryWrapper<>(entity)
                .like(!StringUtils.isEmpty(entity.getName()), SysDepart::getName, entity.getName())
                .like(!StringUtils.isEmpty(entity.getCode()), SysDepart::getCode, entity.getCode());
    }

    public Page<SysDepart> findAll(Integer pageIndex, Integer pageSize, DepartForm form) {
        return baseMapper.selectPage(Page.of(pageIndex, pageSize), createQueryWrapper(form.toEntity()));
    }

    public List<SysDepart> findAll(DepartForm form) {
        return baseMapper.selectList(createQueryWrapper(form.toEntity()));
    }
}
