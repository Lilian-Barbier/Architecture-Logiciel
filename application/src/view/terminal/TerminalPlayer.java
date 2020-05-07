package view.terminal;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import model.list.Playlist;
import view.graphic.GraphicPlayer;

public class TerminalPlayer {
	
	private final String play = "play";
	private final String pause = "pause";
	private final String forward = "forward";
	private final String backward = "backward";
	private final String nextList = "nextList";
	private final String previousList = "previousList";
	
	
	
	private Playlist model;
	
	// CONSTRUCTEURS
	public TerminalPlayer() {
		createModel();
        createController();
	}

	private void createModel() {
        model = new Playlist();
    }
		
	private void createController() {
		
		/*model.addObserver(new Observer() {
		@Override
			public void update(Observable o, Object arg) {
				refresh();
			}
		});*/
		

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
        	String line = reader.readLine();
			//while(line != null && model.isFinish()) {
        	while(line != null) {
				
				if(line.equals(play)) {
					//model.play();
				}
				
				if(line.equals(pause)) {
					//model.play();
				}
				
				if(line.equals(forward)) {
					//model.play();
				}
				
				
				if(line.equals(backward)) {
					//model.play();
				}
				
				
				if(line.equals(nextList)) {
					//model.play();
				}
				
				
				if(line.equals(previousList)) {
					//model.play();
				}
				
				System.out.println(line);
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
		new TerminalPlayer();
	}

}