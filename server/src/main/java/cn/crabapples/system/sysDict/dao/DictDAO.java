package cn.crabapples.system.sysDict.dao;

import cn.crabapples.system.sysDict.dao.mybatis.mapper.DictMapper;
import cn.crabapples.system.sysDict.entity.SysDict;
import cn.crabapples.system.sysDict.form.DictForm;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class DictDAO extends ServiceImpl<DictMapper, SysDict> {
    public Page<SysDict> findAll(Integer pageIndex, Integer pageSize, DictForm form) {
        return mapper.paginate(Page.of(pageIndex, pageSize), QueryWrapper.create(form.toEntity()));
    }
}
