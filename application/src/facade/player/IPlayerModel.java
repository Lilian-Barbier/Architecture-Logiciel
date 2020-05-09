package facade.player;

import java.io.File;

import model.list.IMedia;
import model.list.SubList;
import model.playlist.Playlist;
import view.Observer;

public interface IPlayerModel {

	/**
	 * Retourne la Playlist racine géré par la facade.
	 * @return
	 */
	Playlist getRootPlaylist();

	/**
	 * Retourne le temps passé sur le fichier courant.
	 * @return
	 */
	int getCurrentTime();

	/**
	 * Retourne les informations concernant la listes racines.
	 * @return
	 */
	String getInfos();

	/**
	 * Définis la liste Racine.
	 * @param playlist
	 */
	void setRootPlaylist(Playlist playlist);

	/**
	 * Définis le temps passé sur le fichier courant, à la valeur donnée en paramétre.
	 * @param time
	 */
	void setCurrentTime(int time);

	/**
	 * Charge les données d'une playlist à partir d'un fichier.
	 * @param f
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

	/**
	 * Retourne le chemin des sous listes jusqu'au fichier courant.
	 * @return SousListes
	 */
	String getPathSubList();

	/**
	 * Retourne l'élément courant.
	 * @return
	 */
	IMedia getCurrentFile();

	/**
	 * Retourne la premiére sous-liste parent.
	 * @return
	 */
	SubList getParentCurrentFile();

	
	public void attach(Observer o);
	
	public void dettach(Observer o);
}
