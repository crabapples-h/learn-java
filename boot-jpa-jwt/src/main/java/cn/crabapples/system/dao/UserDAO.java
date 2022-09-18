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
    private final UserRepository userRepository;

    public UserDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long count() {
        return userRepository.countByDelFlag(DIC.NOT_DEL);
    }

    public List<SysUser> findAll(int delFlag) {
        return userRepository.findByDelFlag(delFlag);
    }

    public List<SysUser> findAll() {
        return userRepository.findAll();
    }

    public Page<SysUser> findAll(PageDTO page, int delFlag) {
        Pageable pageable = PageRequest.of(page.getPageIndex(), page.getPageSize(), ASC_CREATE_TIME);
        return userRepository.findByDelFlag(pageable, delFlag);
    }

    public Page<SysUser> findAll(PageDTO page) {
        Pageable pageable = PageRequest.of(page.getPageIndex(), page.getPageSize(), ASC_CREATE_TIME);
        return userRepository.findAll(pageable);
    }

    public SysUser findByUsername(String username) {
        Optional<SysUser> optional = userRepository.findByDelFlagAndUsername(DIC.NOT_DEL, username);
        return optional.orElse(null);
    }

    public SysUser findById(String id) {
        Optional<SysUser> optional = userRepository.findByDelFlagAndId(DIC.NOT_DEL, id);
        return checkOptional(optional);
    }

    public List<SysUser> findById(List<String> ids) {
        return userRepository.findByDelFlagAndIdIn(DIC.NOT_DEL, ids);
    }

    public List<SysUser> findByName(String name) {
        return userRepository.findByDelFlagAndNameContaining(DIC.NOT_DEL, name);
    }

    public SysUser save(SysUser user) {
        return userRepository.save(user);
    }

    public void delUser(String id) {
        userRepository.delUser(id, DIC.IS_DEL);
    }

    public void lockUser(String id) {
        userRepository.lockUnlockUser(id, DIC.USER_LOCK);
    }

    public void unlockUser(String id) {
        userRepository.lockUnlockUser(id, DIC.USER_UNLOCK);
    }
}
