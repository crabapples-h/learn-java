package pattern23.observer;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pattern23.adapter_obj.Client;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Obj {
    private final static Logger logger = LoggerFactory.getLogger(Client.class);
    private List<Observer> observers = new ArrayList<>();

    public void update() {
        logger.info("update");
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
