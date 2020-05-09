package facade.player;

import view.Observer;

public interface IPlayerObserver {

	public void attach(Observer o);
	
	public void dettach(Observer o);
	
	public void notifyObservers();
	
}
