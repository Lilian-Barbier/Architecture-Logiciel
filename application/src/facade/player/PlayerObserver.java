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
	
	public void notifyObserversTime(int time) {
		for(Observer o : observers) {
			o.updateTime(time);
		}
	}
	
	public void notifyObserversFile(String newInfos) {
		for(Observer o : observers) {
			o.updateFile(newInfos);
		}
	}
	
}
