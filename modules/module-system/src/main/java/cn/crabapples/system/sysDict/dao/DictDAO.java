package cn.crabapples.system.sysDict.dao;

import cn.crabapples.system.sysDepart.entity.SysDepart;
import cn.crabapples.system.sysDict.dao.mybatis.mapper.DictMapper;
import cn.crabapples.system.sysDict.entity.SysDict;
import cn.crabapples.system.sysDict.form.DictForm;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class DictDAO extends ServiceImpl<DictMapper, SysDict> {
    private LambdaQueryWrapper<SysDict> createQueryWrapper(SysDict entity) {
        return new LambdaQueryWrapper<>(entity)
                .like(!StringUtils.isEmpty(entity.getName()), SysDict::getName, entity.getName())
                .like(!StringUtils.isEmpty(entity.getCode()), SysDict::getCode, entity.getCode());
    }

    public Page<SysDict> findAll(Integer pageIndex, Integer pageSize, DictForm form) {
        return baseMapper.selectPage(Page.of(pageIndex, pageSize), createQueryWrapper(form.toEntity()));
    }
}
