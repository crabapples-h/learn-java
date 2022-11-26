package cn.crabapples.system.dao;

import cn.crabapples.common.DIC;
import cn.crabapples.common.PageDTO;
import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.system.dao.jpa.UserRepository;
import cn.crabapples.system.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDAO extends BaseDAO {
    private final UserRepository repository;

    public UserDAO(UserRepository repository) {
        this.repository = repository;
    }

    public long count() {
        return repository.countByDelFlag(DIC.NOT_DEL);
    }

    public List<SysUser> findAll(int delFlag) {
        return repository.findByDelFlag(delFlag);
    }

    public List<SysUser> findAll() {
        return repository.findAll();
    }

    public Page<SysUser> findAll(PageDTO page, int delFlag) {
        Pageable pageable = PageRequest.of(page.getPageIndex(), page.getPageSize(), ASC_CREATE_TIME);
        return repository.findByDelFlag(pageable, delFlag);
    }

    public Page<SysUser> findAll(PageDTO page) {
        Pageable pageable = PageRequest.of(page.getPageIndex(), page.getPageSize(), ASC_CREATE_TIME);
        return repository.findAll(pageable);
    }

    public SysUser findByUsername(String username) {
        Optional<SysUser> optional = repository.findByDelFlagAndUsername(DIC.NOT_DEL, username);
        return optional.orElse(null);
    }

    public SysUser findById(String id) {
        Optional<SysUser> optional = repository.findByDelFlagAndId(DIC.NOT_DEL, id);
        return checkOptional(optional);
    }

    public List<SysUser> findById(List<String> ids) {
        return repository.findByDelFlagAndIdIn(DIC.NOT_DEL, ids);
    }

    public List<SysUser> findByName(String name) {
        return repository.findByDelFlagAndNameContaining(DIC.NOT_DEL, name);
    }

    public SysUser save(SysUser user) {
        return repository.save(user);
    }

    public void delUser(String id) {
        repository.delUser(id, DIC.IS_DEL);
    }

    public void lockUser(String id) {
        repository.lockUnlockUser(id, DIC.USER_LOCK);
    }

    public void unlockUser(String id) {
        repository.lockUnlockUser(id, DIC.USER_UNLOCK);
    }
}
