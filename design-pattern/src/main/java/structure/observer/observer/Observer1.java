package structure.observer.observer;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import structure.observer.Observer;

@Slf4j
public class Observer1 implements Observer {
    private final static Logger logger = LoggerFactory.getLogger(Observer1.class);

    @Override
    public void notifyObj() {
        logger.info("Observer1收到了通知");
    }
}
