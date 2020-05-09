package facade.player;

import view.Observer;

public interface IObservable {
	public void attach(Observer o);
	
	public void dettach(Observer o);
	
	public void notifyObservers();
}
