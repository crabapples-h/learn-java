package create.factory.factorymethod.crabapples;

import create.factory.factorymethod.Flower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 海棠
 */
public class Crabapples implements Flower {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void show() {
        logger.info("这是玫瑰:[{}]", this);
    }
}
