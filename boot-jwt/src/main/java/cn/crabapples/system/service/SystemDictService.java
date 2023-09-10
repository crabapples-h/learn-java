package cn.crabapples.system.service;

import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.entity.SysDict;
import cn.crabapples.system.form.DictForm;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface SystemDictService extends BaseService {
    IPage<SysDict> getDictPage(Integer pageIndex, Integer pageSize, DictForm form);
}
