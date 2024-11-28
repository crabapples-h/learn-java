package create.factory.simplefactory;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO 工厂模式-简单工厂模式(静态工厂模式)
 *
 * @author Mr.He
 * 12/20/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public interface Food {
    void eat();

    @Slf4j
    class Apple implements Food {
        @Override
        public void eat() {
            log.info("吃苹果:[{}]", this);
        }
    }

    @Slf4j
    class Orange implements Food {
        @Override
        public void eat() {
            log.info("吃橙子:[{}]", this);
        }
    }

    @Slf4j
    class Peach implements Food {
        @Override
        public void eat() {
            log.info("吃桃:[{}]", this);
        }
    }


}
