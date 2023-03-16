package pattern23.observer;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Obj {
    private List<Observer> observers = new ArrayList<>();

    public void update() {
        log.info("update");
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
