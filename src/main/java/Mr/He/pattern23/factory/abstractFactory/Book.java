package Mr.He.pattern23.factory.abstractFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 工厂模式-抽象工厂
 *
 * @author Mr.He
 * 12/26/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public interface Book {
    void read();

    class ChineseBook implements Book{
        private Logger logger = LoggerFactory.getLogger(this.getClass());
        @Override
        public void read() {
            logger.info("read ChineseBook:[{}]",this);
        }
    }
    class MathBook implements Book{
        private Logger logger = LoggerFactory.getLogger(this.getClass());
        @Override
        public void read() {
            logger.info("read MathBook:[{}]",this);
        }
    }
}
