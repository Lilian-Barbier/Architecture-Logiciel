package facade.player;

import java.io.File;

import model.list.IMedia;
import model.list.SubList;
import model.playlist.IPlaylist;

@SuppressWarnings("unused")
public interface IPlayerModel extends IPlayerSubject {

	// METHODES

	/**
	 * Retourne la Playlist racine géré par la facade.
	 * @return this.rootPlaylist
	 */
	IPlaylist getRootPlaylist();

	/**
	 * Retourne le temps passé sur le fichier courant.
	 * @return this.currentTime
	 */
	int getCurrentTime();

	/**
	 * Retourne le chemin des sous listes jusqu'au fichier courant.
	 * @return this.pathSubList
	 */
	String getPathSubList();

	/**
	 * Retourne l'élément courant.
	 * @return la Playlist dans laquelle nous somme en train de lire.
	 */
	IMedia getCurrentFile();

	/**
	 * Retourne la premiére sous-liste parent.
	 * @return la sous-liste parente de la courrante, si cela est possible.
	 */
	SubList getParentCurrentFile();


	/**
	 * Affiche les informations associés à la Playlist
	 * @return les informations sous forme de String
	 */
	String getInfos();

	// COMMANDES

	/**
	 * Définis la liste racine.
	 * @param playlist la Playlist à définir en temps que racine
	 */
	void setRootPlaylist(IPlaylist playlist);

	/**
	 * Définis le temps passé sur le fichier courant, à la valeur donnée en paramétre.
	 * @param time le temps à remplacer
	 */
	void setCurrentTime(int time);

	/**
	 * Charge les données d'une Playlist à partir d'un fichier.
	 * @param f le fichier source de la Playlist
	 */
	void load(File f);

	/**
	 * Démare la lecture de l'élément courant.
	 */
	void play();

	/**
	 * Met en pause la lecture de l'élément courant.
	 */
	void pause();

	/**
	 * Stop le player.
	 */
	void stop();

	/**
	 * Passe à l'élément suivant dans la playlist.
	 */
	void forward();

	/**
	 * Passe à l'élément précédent dans la playlist.
	 */
	void backward();

	/**
	 * Termine la sous-liste actuelle et démarre la lecture de l’entrée suivante dans la liste parent
	 */
	void nextList();

	/**
	 * Termine la sous-liste actuelle et revient à l’entrée précédente dans la liste parent.
	 */
	void previousList();
}
