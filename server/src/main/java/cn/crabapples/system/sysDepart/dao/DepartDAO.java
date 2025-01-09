package cn.crabapples.system.sysDepart.dao;

import cn.crabapples.system.sysDepart.dao.mybatis.mapper.DepartMapper;
import cn.crabapples.system.sysDepart.entity.SysDepart;
import cn.crabapples.system.sysDepart.form.DepartForm;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartDAO extends ServiceImpl<DepartMapper, SysDepart> {
    public Page<SysDepart> findAll(Integer pageIndex, Integer pageSize, DepartForm form) {
        return mapper.paginate(Page.of(pageIndex, pageSize), QueryWrapper.create(form.toEntity()));
    }

    public List<SysDepart> findAll(DepartForm form) {
        return mapper.selectListByQuery(QueryWrapper.create(form.toEntity()));
    }
}
