package view.graphic;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import facade.player.StdPlayerModel;
import view.Observer;
import view.terminal.TerminalPlayer;

public class GraphicPlayer implements Observer {
	
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
	private JButton forward;
	private JButton backward;
	private JButton nextList;
	private JButton previousList;
	
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
	
	private void createModel(File playlistFile) {
        model = new StdPlayerModel(playlistFile);
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
    	forward = new JButton("Next");
    	backward = new JButton("Previous");
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
				 q.add(backward);
				 q.add(play);
				 q.add(pause);
				 q.add(forward);
				 q.add(nextList);
			 } 
			 p.add(q);
		 }
		 
		 mainFrame.add(p, BorderLayout.CENTER);
	 }
	 
		
	private void createController() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    
		model.attach(this);
		
        play.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		model.play();
            }
        });
        
        pause.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		model.pause();
            }
        });
        
        forward.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		model.forward();
            }
        });
        
        backward.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		model.backward();
            }
        });
        
        nextList.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		model.nextList();
            }
        });
        
        previousList.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		model.previousList();
            }
        });
	}	

	// POINT D'ENTREE
	public static void main(String[] args) {

		if(args.length != 1){
			new AssertionError("TerminalPlayer main() : Argument manquant !");
		}
		
		File f = new File(args[0]);
	
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GraphicPlayer(f).display();
			}
		});
	}


	@Override
	public void update() {
		String newInfos = "<html>";
		newInfos += model.getInfos().replace(";", "<br/>");
		newInfos += "</html>";
				
		int time = model.getCurrentTime();
		int mediaDuration = model.getMediaDuration();
		
		timeProgress.setText("" + time);
		progressBar.setValue(time);
		progressBar.setMaximum(mediaDuration);
		fullTime.setText("" + mediaDuration);
		infosCurrentFile.setText(newInfos);
	}

}