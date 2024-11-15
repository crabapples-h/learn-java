package create.factory.factorymethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 工厂模式-工厂方法模式
 *
 * @author Mr.He
 * 12/20/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class RoseFactory implements FlowerFactory {
    @Override
    public Flower createFlower() {
        return new Rose();
    }

    static class Rose implements Flower {
        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        @Override
        public void show() {
            logger.info("这是玫瑰:[{}]", this);
        }
    }
}

