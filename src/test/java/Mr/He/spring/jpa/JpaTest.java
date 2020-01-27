package Mr.He.spring.jpa;

import Mr.He.spring.entity.User;
import Mr.He.spring.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * TODO
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
    UserService userService;
    @Test
    public void case1() {
        List<User> names = userService.findByName("kitty");
        List<User> hqls = userService.findByHQL("kitty");
        System.err.println(names);
        System.err.println(hqls);
        names.forEach(System.out::println);
        hqls.forEach(System.out::println);
    }
}

