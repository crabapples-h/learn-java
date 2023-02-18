package cn.crabapples.test.dao;

import cn.crabapples.common.BaseDAO;
import cn.crabapples.common.BaseRepository;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.test.dao.jpa.UserRepositoryTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TODO 用户相关服务测试DAO
 *
 * @author Mr.He
 * 2022/11/27 0:20
 * pc-name mrhe
 */
@Component
public class UserDAOTest extends BaseDAO<SysUser, String> {
    private final UserRepositoryTest repository;

    public UserDAOTest(UserRepositoryTest repository) {
        this.repository = repository;
    }

    public List<SysUser> findByHQL(String name) {
        return repository.findByHQL(name);
    }

    public List<SysUser> findBySQL(String name) {
        return repository.findBySQL(name);
    }

    public List<SysUser> findAll() {
        return super.findAll(repository);
    }

    public List<SysUser> findAll(Example<SysUser> example) {
        return super.findAll(repository, example);
    }

    public List<SysUser> findAll(Specification<SysUser> specification) {
        return super.findAll(repository, specification);
    }
}
