package cn.crabapples.system.service.impl;

import cn.crabapples.system.dao.DictDAO;
import cn.crabapples.system.entity.SysDict;
import cn.crabapples.system.form.DictForm;
import cn.crabapples.system.service.SystemDictService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        return dictDAO.findAll(pageIndex,pageSize,form);
    }
}
