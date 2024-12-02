package structure.observer;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Obj {
    private final static Logger logger = LoggerFactory.getLogger(Obj.class);
    private final List<Observer> observers = new ArrayList<>();

    public void update() {
        logger.info("Obj对象被更新了");
        notifyObj();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserve(Observer observer) {
        observers.remove(observer);
    }


    private void notifyObj() {
        for (Observer observer : observers) {
            observer.notifyObj();
        }
    }
}
