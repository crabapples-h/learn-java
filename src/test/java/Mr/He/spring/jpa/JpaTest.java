package Mr.He.spring.jpa;

import Mr.He.spring.dao.UserRepository;
import Mr.He.spring.entity.User;
import org.junit.Assert;
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
    UserRepository userRepository;
    @Test
    public void case1() {
        List<User> users = userRepository.findByName("kitty");
        System.err.println(users);
        users.forEach(System.out::println);
    }
}

