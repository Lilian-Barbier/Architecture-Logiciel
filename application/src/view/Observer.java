package view;

public interface Observer {
	/**
	 * Permet la mise à jour de la vue en récupérant les informations du modèle via un Controller, patron MVC
	 */
	void update();
}
