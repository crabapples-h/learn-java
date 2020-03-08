package cn.crabapples.spring.jpa;

import cn.crabapples.spring.dao.SysMenuRepository;
import cn.crabapples.spring.entity.SysMenu;
import cn.crabapples.spring.entity.SysRole;
import cn.crabapples.spring.entity.SysUser;
import cn.crabapples.spring.service.UserService;
import cn.crabapples.spring.test.service.UserServiceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

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
public class JpaTest {
    @Autowired
    UserServiceTest userServiceTest;
    @Autowired
    UserService userService;

    @Autowired
    SysMenuRepository sysMenuRepository;

    @Test
    public void jpaTest() {
        List<SysUser> sql = userServiceTest.findBySQL("kitty");
        List<SysUser> hql = userServiceTest.findByHQL("kitty");
        sql.forEach(System.out::println);
        System.out.println("------------分割线-------------");
        hql.forEach(System.out::println);
    }

    @Test
    public void getAllUserTest() {
        userService.findAll().forEach(e -> {
            Set<SysRole> sysRoles = e.getSysRoles();
            sysRoles.forEach(t -> {
                Set<SysMenu> sysMenus = t.getSysMenus();
                sysMenus.forEach(System.err::println);
            });
        });
    }

    @Test
    public void getMenuTest() {
        System.err.println(sysMenuRepository.findByParentIdIsNull());
    }
}

