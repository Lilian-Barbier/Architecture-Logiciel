package view.graphic;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import model.list.Playlist;

public class GraphicPlayer {
	
	private final String PATH_PLAY_BUTTON = "../../images/play.png";
	private final String PATH_PAUSE_BUTTON = "../../images/pause.png";
	private final String PATH_NEXT_BUTTON = "../../images/next.png";
	private final String PATH_PREVIOUS_BUTTON = "../../images/previous.png";
	private final String PATH_NEXT_LIST_BUTTON = "../../images/nextList.png";
	private final String PATH_PREVIOUS_LIST_BUTTON = "../../images/previousList.png";
	
	
	// ATTRIBUTS
	private JFrame mainFrame;
	private JLabel infosCurrentFile;
	

	private JLabel timeProgress;
	private JLabel fullTime;
	private JProgressBar progressBar;
	
	private JButton play;
	private JButton pause;
	private JButton next;
	private JButton previous;
	private JButton nextList;
	private JButton previousList;
	
	private Playlist model;
	
	// CONSTRUCTEURS
	public GraphicPlayer() {
		createModel();
        createView();
        placeComponents();
        createController();
	}
	
	/**
	 * Rend l'application visible au centre de l'écran.
	 */
	public void display() {
		refresh();
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
	}
	
	private void createModel() {
        model = new Playlist();
    }
	
	private void createView() {
        mainFrame = new JFrame("Graphic Player");
        
        infosCurrentFile = new JLabel("Name : xxx - Artiste : xxx - Duree : xxx");
        timeProgress = new JLabel("0:15");
    	fullTime = new JLabel("1:35");
    	
    	progressBar = new JProgressBar(0,95);
    	progressBar.setEnabled(false);
    	
        play = new JButton("play");
    	pause = new JButton("Pause");
    	next = new JButton("Next");
    	previous = new JButton("Previous");
    	nextList = new JButton("NextList");
    	previousList = new JButton("PreviousList");
	}
	

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
				 q.add(previous);
				 q.add(play);
				 q.add(pause);
				 q.add(next);
				 q.add(nextList);
			 } 
			 p.add(q);
		 }
		 
		 mainFrame.add(p, BorderLayout.CENTER);
	 }
	 
		
	private void createController() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    
		/*model.addObserver(new Observer() {
		@Override
			public void update(Observable o, Object arg) {
				refresh();
			}
		});
		
		... Gérer les bouton
		*/
	}	

	private void refresh() {
		 /*
		 if (model.canGetChange()) {
			 changeInfo.setText("Cet appareil rend la monnaie");
		 } else {
			 changeInfo.setText("Cet appareil ne rend pas la monnaie");
	     }
	     creditInfo.setText("Vous disposez d'un crédit de "
	    		 + model.getCreditAmount() + " cents");
	     if (model.getLastDrink() == null) {
	    	 drinkOutput.setText("");
	     } else {
	    	 drinkOutput.setText(model.getLastDrink().toString());
	     }
	     changeOutput.setText("" + model.getChangeAmount());
	     for (final JButton b : buttonToDrinkRelation.keySet()) {
	    	 if (model.getDrinkNb(buttonToDrinkRelation.get(b)) == 0) {
	    		 b.setEnabled(false);
	    	 } else {
	    		 b.setEnabled(true);
	    	 }
	     }*/
	}
	
	
	// POINT D'ENTREE
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GraphicPlayer().display();
			}
		});
	}

}