package cn.crabapples;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
//@WebAppConfiguration
public class DemoSystemApplicationTest {
    @Test
    public void case1() {
        System.err.println(1);
        Assert.assertTrue(true);
    }
}
