package cn.crabapples.system.sysDict.service.impl;

import cn.crabapples.system.sysDict.dao.DictDAO;
import cn.crabapples.system.sysDict.entity.SysDict;
import cn.crabapples.system.sysDict.entity.SysDictItem;
import cn.crabapples.system.sysDict.form.DictForm;
import cn.crabapples.system.sysDict.form.DictItemForm;
import cn.crabapples.system.sysDict.service.SystemDictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SystemDictServiceImpl implements SystemDictService {
    private final DictDAO dictDAO;

    public SystemDictServiceImpl(DictDAO dictDAO) {
        this.dictDAO = dictDAO;
    }


    @Override
    public IPage<SysDict> getDictPage(Integer pageIndex, Integer pageSize, DictForm form) {
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
        return form.toEntity().insertOrUpdate();
    }

    @Override
    public List<SysDictItem> getDictItemListByCode(String code) {
        SysDictItem sysDictItem = SysDictItem.create().setDictCode(code);
        return sysDictItem.selectList(new QueryWrapper<>(sysDictItem));
    }

    @Override
    public List<SysDictItem> getDictItemListById(String id) {
        SysDict sysDict = SysDict.create().setId(id).selectById();
        return getDictItemListByCode(sysDict.getCode());
    }

    @Override
    public boolean deleteItemById(String id) {
        return SysDictItem.create().setId(id).deleteById();
    }
}
