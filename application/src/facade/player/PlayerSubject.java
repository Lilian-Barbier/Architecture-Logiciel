package facade.player;

import java.util.ArrayList;
import java.util.List;

import view.Observer;

public class PlayerSubject implements IPlayerSubject{

	private List<Observer> observers;
	
	public PlayerSubject() {
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
