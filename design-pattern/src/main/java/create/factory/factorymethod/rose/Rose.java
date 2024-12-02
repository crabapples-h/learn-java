package create.factory.factorymethod.rose;

import create.factory.factorymethod.Flower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 玫瑰
 */
public class Rose implements Flower {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void show() {
        logger.info("这是玫瑰:[{}]", this);
    }
}
