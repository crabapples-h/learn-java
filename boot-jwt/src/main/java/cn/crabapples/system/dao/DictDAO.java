package cn.crabapples.system.dao;

import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.system.dao.jpa.DictRepository;
import cn.crabapples.system.entity.SysUser;
import org.springframework.stereotype.Component;

@Component
public class DictDAO extends BaseDAO<SysUser, String> {
    private final DictRepository repository;

    public DictDAO(DictRepository repository) {
        this.repository = repository;
    }
}
