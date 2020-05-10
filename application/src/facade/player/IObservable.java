package facade.player;

import view.Observer;

public interface IObservable {

	/**
	 * Ajoute une Observer au model
	 * @param o l'Observer à ajouter
	 */
	void attach(Observer o);

	/**
	 * Retire un Observer au model
	 * @param o l'Observer à retirer
	 */
	void dettach(Observer o);

	/**
	 * Signale une mise à jour du model aux Observers attachés au model
	 */
	void notifyObservers();
}
