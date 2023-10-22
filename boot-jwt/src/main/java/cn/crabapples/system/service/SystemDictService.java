package cn.crabapples.system.service;

import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.entity.SysDict;
import cn.crabapples.system.entity.SysDictItem;
import cn.crabapples.system.form.DictForm;
import cn.crabapples.system.form.DictItemForm;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface SystemDictService extends BaseService {
    IPage<SysDict> getDictPage(Integer pageIndex, Integer pageSize, DictForm form);

    boolean saveDict(DictForm form);

    boolean deleteById(String id);

    boolean saveDictItem(DictItemForm form);

    List<SysDictItem> getDictItemListByCode(String code);

    List<SysDictItem> getDictItemListById(String id);

    boolean deleteItemById(String id);

}
