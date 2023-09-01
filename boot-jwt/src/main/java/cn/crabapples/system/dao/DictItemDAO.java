package cn.crabapples.system.dao;

import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.system.dao.mybatis.DictItemMapper;
import cn.crabapples.system.entity.SysDictItem;
import org.springframework.stereotype.Component;

@Component
public class DictItemDAO extends BaseDAO<SysDictItem, String> {
    private final DictItemMapper mapper;

    public DictItemDAO(DictItemMapper mapper) {
        this.mapper = mapper;
    }
}
