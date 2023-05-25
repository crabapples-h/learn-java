package cn.crabapples.system.dao;

import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.system.dao.jpa.DictItemRepository;
import cn.crabapples.system.entity.SysDictItem;
import cn.crabapples.system.entity.SysUser;
import org.springframework.stereotype.Component;

@Component
public class DictItemDAO extends BaseDAO {
    private final DictItemRepository repository;

    public DictItemDAO(DictItemRepository repository) {
        this.repository = repository;
    }
}
