package facade.player;

import view.Observer;

@SuppressWarnings("unused")
public interface IPlayerSubject {

	/**
	 * Attache un Observer
	 * @param o l'Observer à attacher au modèle
	 */
	void attach(Observer o);

	/**
	 * Détache un Observer
	 * @param o l'Observer à détacher du modèle
	 */
	void dettach(Observer o);
	
}
