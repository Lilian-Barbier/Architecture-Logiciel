package view.terminal;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import facade.player.StdPlayerModel;
import model.xml.XMLPlaylistLoader;
import view.Observer;

public class TerminalPlayer implements Observer {
	
	private static final String PLAY = "play";
	private static final String PAUSE = "pause";
	private static final String STOP = "stop";
	private static final String FORWARD = "forward";
	private static final String BACKWARD = "backward";
	private static final String NEXT_LIST = "nextList";
	private static final String PREVIOUS_LIST = "previousList";
	private static final String HELP = "help";
	
	private StdPlayerModel model;
	
	// CONSTRUCTEURS

	public TerminalPlayer(File playlistFile) {
		createModel(playlistFile);
        createController();
	}

	/**
	 * Instancie le model
	 * @param playlistFile le fichier Playlist à charger au démarrage
	 */
	private void createModel(File playlistFile) {
        model = new StdPlayerModel(playlistFile);
    }

	/**
	 * Création des Controller
	 */
	private void createController() {
		model.attach(this);
		System.out.println("Pour une aide : " + HELP);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
        	String line = reader.readLine();
        	while (line != null) {
        		switch (line) {
					case PLAY:
						model.play();
						break;
					case PAUSE:
						model.pause();
						break;
					case STOP:
						model.stop();
						break;
					case FORWARD:
						model.forward();
						break;
					case BACKWARD:
						model.backward();
						break;
					case NEXT_LIST:
						model.nextList();
						break;
					case PREVIOUS_LIST:
						model.previousList();
						break;
					case HELP:
						System.out.println(PLAY + " : démarrer la lecture de la playlist.");
						System.out.println(PAUSE + " : met en pause la lecture de la playlist.");
						System.out.println(FORWARD + " : passe au fichier suivant de la playlist.");
						System.out.println(BACKWARD + " : revient au fichier précédent de la playlist.");
						System.out.println(NEXT_LIST + " : termine la sous-liste actuelle et démarre la lecture de l’entrée suivante dans la liste parent.");
						System.out.println(PREVIOUS_LIST + " : termine la sous-liste actuelle et revient à l’entrée précédente dans la liste parent.");
						System.out.println(STOP + " : arrête la lecture et réinitialise la tête de lecture");

						break;
					default:
						System.out.println(line + " : commande introuvable.");
						System.out.println("Pour une aide : " + HELP);
						break;
				}
				line = reader.readLine();
			}
        } catch (IOException e) {
        	// Problème lors de la lecture
			System.out.println("Erreur lors de la lecture d'un fichier, le programme doit se terminer.");
			System.exit(-1);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// Problème lors de la fermeture
				System.out.println("Erreur lors de la fermeture d'un fichier, le programme doit se terminer.");
				System.exit(-1);
			}
		}
	
	}

	@Override
	public void update() {
		String newInfos = model.getCurrentFile().getName();
		int time = model.getCurrentTime();
		int mediaDuration = model.getCurrentFile().getDuration();
		System.out.print("\r" + newInfos + " : " + time + "/" + mediaDuration);
	}

	// POINT D'ENTREE
	public static void main(String[] args) {
		if (args.length != 1){
			System.out.println("TerminalPlayer main() : Argument manquant !");
			System.exit(-1);
		}
		File f = new File(args[0]);
		new TerminalPlayer(f);
	}
}