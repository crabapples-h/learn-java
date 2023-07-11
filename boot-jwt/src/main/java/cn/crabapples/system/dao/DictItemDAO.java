package cn.crabapples.system.dao;

import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.system.dao.jpa.DictItemRepository;
import cn.crabapples.system.entity.SysUser;
import org.springframework.stereotype.Component;

@Component
public class DictItemDAO extends BaseDAO<SysUser, String> {
    private final DictItemRepository repository;

    public DictItemDAO(DictItemRepository repository) {
        this.repository = repository;
    }
}
