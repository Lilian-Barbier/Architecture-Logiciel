package view.terminal;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import facade.player.StdPlayerModel;
import view.Observer;

public class TerminalPlayer implements Observer {
	
	private final String play = "play";
	private final String pause = "pause";
	private final String stop = "stop";
	private final String forward = "forward";
	private final String backward = "backward";
	private final String nextList = "nextList";
	private final String previousList = "previousList";
	private final String help = "help";
	
	private StdPlayerModel model;
	
	// CONSTRUCTEURS
	public TerminalPlayer(File playlistFile) {
		createModel(playlistFile);
        createController();
	}

	private void createModel(File playlistFile) {
        model = new StdPlayerModel(playlistFile);
    }
		
	private void createController() {
		
		model.attach(this);
		
		System.out.println("Pour une aide : " + help);
		
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
        	String line = reader.readLine();
			//while(line != null && model.isFinish()) {
        	while(line != null) {
				
        		switch (line) {
				case play:
					model.play();
					break;

				case pause:
					model.pause();
					break;
					
				case stop:
					model.stop();
					break;
					
				case forward:
					model.forward();
					break;
					
				case backward:
					model.backward();
					break;
					
				case nextList:
					model.nextList();
					break;
					
				case previousList:
					model.previousList();
					break;
					
				case help:
					System.out.println(play + " : démarrer la lecture de la playlist.");
					System.out.println(pause + " : met en pause la lecture de la playlist.");
					System.out.println(forward + " : passe au fichier suivant de la playlist.");
					System.out.println(backward + " : revient au fichier précédent de la playlist.");
					System.out.println(nextList + " : termine la sous-liste actuelle et démarre la lecture de l’entrée suivante dans la liste parent.");
					System.out.println(previousList + " : termine la sous-liste actuelle et revient à l’entrée précédente dans la liste parent.");
					break;
					
				default:
					System.out.println(line + " : commande introuvable.");
					System.out.println("Pour une aide : " + help);
					break;
				}
        		
				line = reader.readLine();
			} 
        }
		catch (IOException e) {
				// Problème lors de la lecture
				e.printStackTrace();
		}  
        finally {
			try {
				reader.close();
			} catch (IOException e) {
				// Problème lors de la fermeture
				e.printStackTrace();
			}
		}
	
	}	
	
	// POINT D'ENTREE
	public static void main(String[] args) {
		
		if(args.length != 1){
			new AssertionError("TerminalPlayer main() : Argument manquant !");
		}
		
		File f = new File(args[0]);
		
		new TerminalPlayer(f);
	}

	@Override
	public void update() {
		String newInfos = model.getCurrentFile().getName();
		int time = model.getCurrentTime();
		int mediaDuration = model.getMediaDuration();
		
		System.out.print("\r" + newInfos + " : " + time + "/" + mediaDuration);
	}
	
}