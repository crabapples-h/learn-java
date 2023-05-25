package cn.crabapples.system.dao;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.common.DIC;
import cn.crabapples.common.PageDTO;
import cn.crabapples.system.dao.jpa.RoleRepository;
import cn.crabapples.system.entity.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * TODO 系统角色DAO
 *
 * @author Mr.He
 * 2021/4/13 4:05
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@Component
public class RoleDAO extends BaseDAO{
    private final RoleRepository repository;

    public RoleDAO(RoleRepository repository) {
        this.repository = repository;
    }

    public long count() {
        return repository.count();
    }

    public SysRole findById(String id) {
        Optional<SysRole> optional = repository.findById(id);
        return optional.orElseThrow(() -> new ApplicationException("找不到对应数据"));
    }

    public SysRole save(SysRole entity) {
        return repository.save(entity);
    }

    public Page<SysRole> findAll(PageDTO page) {
        Pageable pageable = PageRequest.of(page.getPageIndex(), page.getPageSize(), ASC_CREATE_TIME);
        return repository.findByDelFlag(pageable, DIC.NOT_DEL);
    }

    public List<SysRole> findAll() {
        return repository.findByDelFlag(DIC.NOT_DEL);
    }

    public List<SysRole> findByIds(List<String> ids) {
        return repository.findByIds(ids);
    }

    public List<SysRole> findByIds(String[] ids) {
        return repository.findByIds(ids);
    }


    public List<SysRole> findByMenusId(String menusId) {
        throw new ApplicationException("暂未实现");
//        return repository.findByDelFlagAndMenusIdsContains(DIC.NOT_DEL, menusId);
    }


}
