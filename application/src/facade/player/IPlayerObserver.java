package facade.player;

import java.util.ArrayList;

import view.Observer;

public interface IPlayerObserver {

	public void attach(Observer o);
	
	public void dettach(Observer o);
	
	public void notifyObserversTimeChange(int time);
	
}
