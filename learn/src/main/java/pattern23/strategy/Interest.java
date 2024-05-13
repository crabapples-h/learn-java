package pattern23.strategy;

import org.slf4j.Logger;

public interface Interest {
    void show();
}

class Eat implements Interest {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Eat.class);

    @Override
    public void show() {
        log.info("eat()");
    }
}

class Sing implements Interest {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Sing.class);

    @Override
    public void show() {
        log.info("sing()");
    }
}
