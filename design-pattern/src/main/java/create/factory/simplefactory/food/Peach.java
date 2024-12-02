package create.factory.simplefactory.food;

import create.factory.simplefactory.Food;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Peach implements Food {
    private static final Logger log = LoggerFactory.getLogger(Peach.class);

    @Override
    public void eat() {
        log.info("吃桃:[{}]", this);
    }
}