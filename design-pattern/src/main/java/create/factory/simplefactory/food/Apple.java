package create.factory.simplefactory.food;

import create.factory.simplefactory.Food;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Apple implements Food {
    private static final Logger log = LoggerFactory.getLogger(Apple.class);

    @Override
    public void eat() {
        log.info("吃苹果:[{}]", this);
    }
}
