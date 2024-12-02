package create.factory.simplefactory.food;

import create.factory.simplefactory.Food;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Orange implements Food {
    private static final Logger log = LoggerFactory.getLogger(Food.class);

    @Override
    public void eat() {
        log.info("吃橙子:[{}]", this);
    }
}