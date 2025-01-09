package cn.crabapples.system.sysDepart.service.impl;

import cn.crabapples.system.sysDepart.dao.DepartDAO;
import cn.crabapples.system.sysDepart.entity.SysDepart;
import cn.crabapples.system.sysDepart.form.DepartForm;
import cn.crabapples.system.sysDepart.service.SystemDepartService;
import com.mybatisflex.core.paginate.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class SystemDepartServiceImpl implements SystemDepartService {
    private final DepartDAO dictDAO;

    public SystemDepartServiceImpl(DepartDAO dictDAO) {
        this.dictDAO = dictDAO;
    }


    @Override
    public Page<SysDepart> page(Integer pageIndex, Integer pageSize, DepartForm form) {
        return dictDAO.findAll(pageIndex, pageSize, form);
    }

    @Override
    public List<SysDepart> list(DepartForm form) {
        return dictDAO.findAll( form);
    }


    @Override
    public boolean saveDepart(DepartForm form) {
        return dictDAO.saveOrUpdate(form.toEntity());
    }

    @Override
    public boolean deleteById(String id) {
        return dictDAO.removeById(id);
    }
}
