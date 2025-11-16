package cn.crabapples.mybatis.plus;

import cn.crabapples.system.sysUser.dao.UserDAO;
import cn.crabapples.system.sysUser.entity.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * TODO mybatis-plus测试类
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
public class MybatisFlexTest {
    @Autowired
    UserDAO dao;

    @Test
    public void case1() {
        List<SysUser> users = dao.list(new LambdaQueryWrapper<>(SysUser.class));
        for (SysUser user : users) {
            log.info("用户:[{}]", user);
        }
    }
}

