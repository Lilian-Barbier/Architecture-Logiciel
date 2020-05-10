package facade.player;

import java.util.ArrayList;
import java.util.List;

import view.Observer;

public class Observable implements IObservable{

    /**
     * L'ensemble d'Observer
     */
    private List<Observer> observers;

    public Observable() {
        observers = new ArrayList<Observer>();
    }

    public void attach(Observer o) {
        observers.add(o);
    }

    public void dettach(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for(Observer o : observers) {
            o.update();
        }
    }
}
