package view.graphic;

import facade.player.StdPlayerModel;
import model.list.IMedia;
import view.Observer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GraphicPlayer implements Observer {
	
	private static final String PATH_PLAY_BUTTON = "src/images/play.png";
	private static final String PATH_PAUSE_BUTTON = "src/images/pause.png";
	private static final String PATH_NEXT_BUTTON = "src/images/next.png";
	private static final String PATH_PREVIOUS_BUTTON = "src/images/previous.png";
	private static final String PATH_NEXT_LIST_BUTTON = "src/images/nextList.png";
	private static final String PATH_PREVIOUS_LIST_BUTTON = "src/images/previousList.png";
	private static final String PATH_STOP_BUTTON = "src/images/stop.png";




	// ATTRIBUTS
	private JFrame mainFrame;
	private JLabel infosCurrentFile;

	private JLabel timeProgress;
	private JLabel fullTime;
	private JProgressBar progressBar;
	
	private JButton play;
	private JButton pause;
	private JButton forward;
	private JButton backward;
	private JButton nextList;
	private JButton previousList;
	private JButton stop;

	private StdPlayerModel model;
	
	// CONSTRUCTEURS

	public GraphicPlayer(File playlistFile) {
		createModel(playlistFile);
        createView();
        placeComponents();
        createController();
	}
	
	/**
	 * Rend l'application visible au centre de l'écran.
	 */
	public void display() {
		update();
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
	}

	/**
	 * Instancie le model
	 * @param playlistFile le fichier Playlist à charger au démarrage
	 */
	private void createModel(File playlistFile) {
        model = new StdPlayerModel(playlistFile);
    }

	/**
	 * Création des différents éléments de la vue
	 */
	private void createView() {
        mainFrame = new JFrame("Graphic Player");
        
        infosCurrentFile = new JLabel();
        timeProgress = new JLabel();
    	fullTime = new JLabel();
    	
    	progressBar = new JProgressBar();
    	progressBar.setEnabled(false);
    	
        play = new JButton(new ImageIcon(PATH_PLAY_BUTTON));
    	pause = new JButton(new ImageIcon(PATH_PAUSE_BUTTON));
    	forward = new JButton(new ImageIcon(PATH_NEXT_BUTTON));
    	backward = new JButton(new ImageIcon(PATH_PREVIOUS_BUTTON));
    	nextList = new JButton(new ImageIcon(PATH_NEXT_LIST_BUTTON));
    	previousList = new JButton(new ImageIcon(PATH_PREVIOUS_LIST_BUTTON));
    	stop = new JButton(new ImageIcon(PATH_STOP_BUTTON));
	}

	/**
	 * Dispose les composants sur la fenêtre
	 */
	 private void placeComponents() {
		 JPanel p = new JPanel(new GridLayout(3, 1)); {
			 //zone d’affichage des informations du fichier en cours de lecture
			 JPanel q = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
				 q.add(infosCurrentFile);
			 }
			 p.add(q);

		 	 //zone affichant la progression de la lecture
			 q = new JPanel(new GridLayout(1, 3)); {
				 JPanel r = new JPanel(); {
					 r.add(timeProgress);
				 }
				 q.add(r);
				 q.add(progressBar);
				 r = new JPanel(); {
					 r.add(fullTime);
				 }
				 q.add(r);
			 } 
			 p.add(q);
			 
			 //zone du panneau de commande
			 q = new JPanel(new GridLayout(1, 6)); {
				 q.add(previousList);
				 q.add(backward);
				 q.add(play);
				 q.add(pause);
				 q.add(stop);
				 q.add(forward);
				 q.add(nextList);
			 } 
			 p.add(q);
		 }
		 mainFrame.add(p, BorderLayout.CENTER);
	 }

	/**
	 * Création des Controller
	 */
	private void createController() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		model.attach(this);
        play.addActionListener(e -> model.play());
        pause.addActionListener(e -> model.pause());
        forward.addActionListener(e -> model.forward());
        backward.addActionListener(e -> model.backward());
        nextList.addActionListener(e -> model.nextList());
        previousList.addActionListener(e -> model.previousList());
        stop.addActionListener(e -> model.stop());
	}

	@Override
	public void update() {
		IMedia currentFile = model.getCurrentFile();
		String newInfos = "<html>";
		newInfos += model.getPathSubList() + "<br/>";
		newInfos += currentFile.getInfos().replace(";", "<br/>") + "<br/>";
		newInfos += "</html>";
		int time = model.getCurrentTime();
		int mediaDuration = currentFile.getDuration();
		timeProgress.setText("" + time);
		progressBar.setValue(time);
		progressBar.setMaximum(mediaDuration);
		fullTime.setText("" + mediaDuration);
		infosCurrentFile.setText(newInfos);
	}

	// POINT D'ENTREE
	public static void main(String[] args) {
		if (args.length != 1){
			System.out.println("TerminalPlayer main() : Argument manquant !");
			System.exit(-1);
		}
		File f = new File(args[0]);
		SwingUtilities.invokeLater(() -> new GraphicPlayer(f).display());
	}
}