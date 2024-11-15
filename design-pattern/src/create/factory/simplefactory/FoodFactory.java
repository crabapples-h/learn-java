package create.factory.simplefactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 工厂模式-简单工厂模式(静态工厂模式)
 *
 * @author Mr.He
 * 12/20/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class FoodFactory {
    public static Apple getApple() {
        return new Apple();
    }

    public static Orange getOrange() {
        return new Orange();
    }

    public static Peach getPeach() {
        return new Peach();
    }

    public static class Orange implements Food {
        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        @Override
        public void eat() {
            logger.info("吃橙子:[{}]", this);
        }
    }

    public static class Peach implements Food {
        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        @Override
        public void eat() {
            logger.info("吃桃:[{}]", this);
        }
    }
}
