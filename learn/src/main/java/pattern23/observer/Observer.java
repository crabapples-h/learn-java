package pattern23.observer;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Observer {
    void notifyObj();
}

@Slf4j
class Observer1 implements Observer {
    private final static Logger logger = LoggerFactory.getLogger(Observer1.class);

    @Override
    public void notifyObj() {
        logger.info("Observer1()");
    }
}
@Slf4j
class Observer2 implements Observer {
    private final static Logger logger = LoggerFactory.getLogger(Observer2.class);

    @Override
    public void notifyObj() {
        logger.info("Observer2()");
    }
}
