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

    public List<SysUser> findAll() {
        return userRepository.findByDelFlag(DIC.NOT_DEL);
    }

    public Page<SysUser> findAll(PageDTO page) {
        Pageable pageable = PageRequest.of(page.getPageIndex(), page.getPageSize(), ASC_CREATE_TIME);
        return userRepository.findByDelFlag(pageable, DIC.NOT_DEL);
    }

    public SysUser findByUsername(String username) {
        Optional<SysUser> optional = userRepository.findByDelFlagAndUsername(DIC.NOT_DEL, username);
        return checkOptional(optional);
    }

    public long countByUsername(String username) {
        return userRepository.countByUsername(username);
    }

    public SysUser findById(String id) {
        Optional<SysUser> optional = userRepository.findByDelFlagAndId(DIC.NOT_DEL, id);
        return checkOptional(optional);
    }

    public SysUser findByIdAndStatus(String id, int status) {
        Optional<SysUser> optional = userRepository.findByDelFlagAndIdAndStatus(DIC.NOT_DEL, id, status);
        return checkOptional(optional);
    }

    public List<SysUser> findByIds(List<String> ids) {
        return userRepository.findByDelFlagAndIdIn(DIC.NOT_DEL, ids);
    }

    public List<SysUser> findByName(String name) {
        return userRepository.findByDelFlagAndName(DIC.NOT_DEL, name);
    }

    public List<SysUser> findByNameLike(String name) {
        return userRepository.findByDelFlagAndNameLike(DIC.NOT_DEL, name);
    }

    public SysUser save(SysUser user) {
        return userRepository.save(user);
    }


}
