package doing.strategy.interest;

import doing.strategy.Interest;
import org.slf4j.Logger;

public class Sing implements Interest {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Sing.class);

    @Override
    public void show() {
        log.info("兴趣是唱歌");
    }
}
