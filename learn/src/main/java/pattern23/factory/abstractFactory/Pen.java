package pattern23.factory.abstractFactory;

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
public interface Pen {
    void write();

    class ChinesePen implements Pen{
        private Logger logger = LoggerFactory.getLogger(this.getClass());
        @Override
        public void write() {
            logger.info("write ChinesePen:[{}]",this);
        }
    }

    class MathPen implements Pen{
        private Logger logger = LoggerFactory.getLogger(this.getClass());
        @Override
        public void write() {
            logger.info("write MathPen:[{}]",this);
        }
    }


}
