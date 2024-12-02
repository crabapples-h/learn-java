package structure.observer.observer;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import structure.observer.Observer;

@Slf4j
public class Observer2 implements Observer {
    private final static Logger logger = LoggerFactory.getLogger(Observer2.class);

    @Override
    public void notifyObj() {
        logger.info("Observer2收到了通知");
    }
}
