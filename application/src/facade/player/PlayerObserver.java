package facade.player;

import java.util.ArrayList;
import java.util.List;

import view.Observer;

public abstract class PlayerObserver implements IPlayerObserver{

	private List<Observer> observers;
	
	public PlayerObserver() {
		observers = new ArrayList<Observer>();
	}
	
	public void attach(Observer o) {
		observers.add(o);
	}
	
	public void dettach(Observer o) {
		observers.remove(o);
	}
	
	public void notifyObserversTimeChange(int time) {
		for(Observer o : observers) {
			o.updatePlayerTime(time);
		}
	}
}
