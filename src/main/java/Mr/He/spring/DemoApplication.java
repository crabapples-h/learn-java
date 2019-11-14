package Mr.He.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * TODO springBoot主启动类
 *
 * @author Mr.He
 * @date 2019/8/5 22:53
 * e-mail wishforyou.xia@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@EnableJpaAuditing
@SpringBootApplication
public class DemoApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        LOGGER.info(">>>>>>>>服务启动成功>>>>>>>>>");
    }
}
