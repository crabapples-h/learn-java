package doing.strategy.interest;

import doing.strategy.Interest;
import org.slf4j.Logger;

public class Eat implements Interest {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Eat.class);

    @Override
    public void show() {
        log.info("兴趣是吃东西");
    }
}