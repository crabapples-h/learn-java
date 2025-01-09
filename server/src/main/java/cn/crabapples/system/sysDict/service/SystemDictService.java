package cn.crabapples.system.sysDict.service;

import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.sysDict.entity.SysDict;
import cn.crabapples.system.sysDict.entity.SysDictItem;
import cn.crabapples.system.sysDict.form.DictForm;
import cn.crabapples.system.sysDict.form.DictItemForm;
import com.mybatisflex.core.paginate.Page;

import java.util.List;

public interface SystemDictService extends BaseService<SysDictItem> {
    Page<SysDict> getDictPage(Integer pageIndex, Integer pageSize, DictForm form);

    boolean saveDict(DictForm form);

    boolean deleteById(String id);

    boolean saveDictItem(DictItemForm form);

    List<SysDictItem> getDictItemListByCode(String code);

    List<SysDictItem> getDictItemListById(String id);

    boolean deleteItemById(String id);

}
