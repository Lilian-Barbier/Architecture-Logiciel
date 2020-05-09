package facade.player;

import view.Observer;

public interface IPlayerSubject {

	/**
	 * Attache un Observeur
	 * @param o
	 */
	public void attach(Observer o);
	
	public void dettach(Observer o);
	
	public void notifyObservers();
	
}
