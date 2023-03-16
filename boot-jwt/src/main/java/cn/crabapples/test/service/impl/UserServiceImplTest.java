package cn.crabapples.test.service.impl;

import cn.crabapples.system.entity.SysUser;
import cn.crabapples.test.dao.UserDAOTest;
import cn.crabapples.test.service.UserServiceTest;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO 用户相关服务测试
 *
 * @author Mr.He
 * 2020/1/27 2:10
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Service
public class UserServiceImplTest implements UserServiceTest {
    private final UserDAOTest userDAOTest;

    public UserServiceImplTest(UserDAOTest userDAOTest) {
        this.userDAOTest = userDAOTest;
    }

    @Override
    public List<SysUser> findByHQL(String name) {
        return userDAOTest.findByHQL(name);
    }

    @Override
    public List<SysUser> findBySQL(String name) {
        return userDAOTest.findBySQL(name);
    }

    @Override
    public List<SysUser> findAll() {
        return userDAOTest.findAll();
    }

    @Override
    public List<SysUser> findAll(Example<SysUser> example) {
        return userDAOTest.findAll(example);
    }

    @Override
    public List<SysUser> findAll(Specification<SysUser> specification) {
        return userDAOTest.findAll(specification);

    }
}
