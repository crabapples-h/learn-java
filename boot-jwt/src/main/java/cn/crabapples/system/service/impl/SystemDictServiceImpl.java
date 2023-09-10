package cn.crabapples.system.service.impl;

import cn.crabapples.system.dao.DictDAO;
import cn.crabapples.system.entity.SysDict;
import cn.crabapples.system.entity.SysDictItem;
import cn.crabapples.system.form.DictForm;
import cn.crabapples.system.form.DictItemForm;
import cn.crabapples.system.service.SystemDictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SystemDictServiceImpl implements SystemDictService {
    @Value("${isCrypt}")
    private boolean isCrypt;
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
    public boolean deleteItemById(String id) {
        return SysDictItem.create().setId(id).deleteById();
    }
}
