package cn.crabapples.system.sysDict.service.impl;

import cn.crabapples.system.sysDict.dao.DictDAO;
import cn.crabapples.system.sysDict.dao.DictItemDAO;
import cn.crabapples.system.sysDict.entity.SysDict;
import cn.crabapples.system.sysDict.entity.SysDictItem;
import cn.crabapples.system.sysDict.form.DictForm;
import cn.crabapples.system.sysDict.form.DictItemForm;
import cn.crabapples.system.sysDict.service.SystemDictService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SystemDictServiceImpl implements SystemDictService {
    private final DictDAO dictDAO;
    private final DictItemDAO dictItemDAO;

    public SystemDictServiceImpl(DictDAO dictDAO, DictItemDAO dictItemDAO) {
        this.dictDAO = dictDAO;
        this.dictItemDAO = dictItemDAO;
    }


    @Override
    public Page<SysDict> getDictPage(Integer pageIndex, Integer pageSize, DictForm form) {
        return dictDAO.findAll(pageIndex, pageSize, form);
    }

    @Override
    public boolean saveDict(DictForm form) {
        return dictDAO.saveOrUpdate(form.toEntity());
    }

    @Override
    public boolean deleteById(String id) {
        return dictDAO.removeById(id);
    }

    @Override
    public boolean saveDictItem(DictItemForm form) {
        return form.toEntity().saveOrUpdate();
    }

    @Override
    public List<SysDictItem> getDictItemListByCode(String code) {
        SysDictItem sysDictItem = SysDictItem.create().setDictCode(code);
        return dictItemDAO.list(QueryWrapper.create(sysDictItem));
    }

    @Override
    public List<SysDictItem> getDictItemListById(String id) {
        SysDict entity = dictDAO.getById(id);
        return getDictItemListByCode(entity.getCode());
    }

    @Override
    public boolean deleteItemById(String id) {
        return dictItemDAO.removeById(id);
    }
}
