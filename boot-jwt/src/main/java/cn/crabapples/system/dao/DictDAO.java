package cn.crabapples.system.dao;

import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.system.dao.mybatis.DictMapper;
import cn.crabapples.system.entity.SysDict;
import org.springframework.stereotype.Component;

@Component
public class DictDAO extends BaseDAO<SysDict, String> {
    private final DictMapper mapper;

    public DictDAO(DictMapper mapper) {
        this.mapper = mapper;
    }
}
