package cn.crabapples.system.service;

import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.entity.SysDict;
import cn.crabapples.system.form.DictForm;


public interface SystemDictService extends BaseService<SysDict> {
    Iterable<SysDict> queryList(DictForm form);

    void save(DictForm form);

    void deleteById(String id);

    SysDict getById(String id);
}
