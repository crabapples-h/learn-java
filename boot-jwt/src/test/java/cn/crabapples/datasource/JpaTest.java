package cn.crabapples.datasource;

import cn.crabapples.system.dao.MenusDAO;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.service.SystemUserService;
import cn.crabapples.test.service.UserServiceTest;
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
public class JpaTest {
    @Autowired
    UserServiceTest userServiceTest;
    @Autowired
    SystemUserService userService;

    @Autowired
    MenusDAO menusDAO;

    @Test
    public void jpaTest() {
        List<SysUser> sql = userServiceTest.findBySQL("kitty");
        List<SysUser> hql = userServiceTest.findByHQL("kitty");
        sql.forEach(System.out::println);
        log.info("------------分割线-------------");
        hql.forEach(System.out::println);
    }

//    @Test
//    public void getAllUserTest() {
//        userService.findAll().forEach(e -> {
//            List<SysRoles> sysRoles = e.getRolesList();
//            sysRoles.forEach(t -> {
//                List<SysMenus> sysMenus = t.getSysMenus();
//                sysMenus.forEach(System.err::println);
//            });
//        });
//    }

}

