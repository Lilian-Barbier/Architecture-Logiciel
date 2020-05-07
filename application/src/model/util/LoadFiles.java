package model.util;

import model.list.IMedia;

public interface LoadFiles {

	/**
	 * Permet de charger le fichier et renvoie l'objet IMedia associé
	 * @param path le chemin du fichier à charger
	 * @return l'objet IMedia associé
	 */
	IMedia loadFile(String path);
	
}
