package cn.crabapples.datasource;

import cn.crabapples.test.dao.mybatis.SysUserMapper;
import cn.crabapples.test.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * TODO jpa测试类
 *
 * @author Mr.He
 * 2020/1/27 14:32
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@Slf4j
public class MybatisTest {
    @Autowired
    SysUserMapper sysUserMapper;

    @Test
    public void mybatisTest() {
        final List<SysUser> sysUsers = sysUserMapper.selectByExample(null);
        System.err.println(sysUsers);
    }

}

