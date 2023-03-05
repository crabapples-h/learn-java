package pattern23.observer;

import lombok.extern.slf4j.Slf4j;

public interface Observer {
    void notifyObj();
}

@Slf4j
class Observer1 implements Observer {
    @Override
    public void notifyObj() {
        log.info("Observer1()");
    }
}
@Slf4j
class Observer2 implements Observer {
    @Override
    public void notifyObj() {
        log.info("Observer2()");
    }
}
