// GameSubject.java
package model;

import java.util.ArrayList;
import java.util.List;

public class GameSubject {
    private final List<GameObserver> observers = new ArrayList<>();

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }
}
