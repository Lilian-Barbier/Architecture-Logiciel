package facade.player;

import model.list.IMedia;
import model.list.SubList;
import model.playlist.Playlist;
import model.xml.XMLPlaylistManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class StdPlayerModel extends PlayerObserver implements IPlayerModel {

    // ATTRIBUTS

    private XMLPlaylistManager manager;
    
    /**
     * L'objet Playlist est la racine de notre playlist.
     */
    private Playlist rootPlaylist;
    
    /**
     * Temps écoulé sur le fichier en cours.
     */
    private int currentTime;
    
    /**
     * On associe pour chaque profondeur parcourus un indice indiquant le media courant.
     */
    private Map<Integer, Integer> headPositions;
    
    
    /**
     * Profondeur de la tête de lecture.
     */
    private int depth;
    
    private Timer timer;

   
    // CONSTRUCTEUR

    /**
     * Constructeur de la façade PlayerModel.
     * @param f : Fichier xpl contenant la playlist à lire.
     */
    public StdPlayerModel(File f) {
        rootPlaylist = new Playlist();
        currentTime = 0;
        headPositions = new TreeMap<>();
        headPositions.put(0,0);
        depth = 0;
        
        timer = new Timer();
        load(f);
        
    }

    // METHODES

    public Playlist getRootPlaylist() {
        return rootPlaylist;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public int getMediaDuration() {
        IMedia current = getCurrentFile();
        return current.getDuration();
    }

    public int getDepth() { return depth; }

    @Override
    public String getInfos() {
        int duration = 0;
        /*for (IMedia list : rootPlaylist.getPlaylist().subList(0, rootPlaylist.getPlaylist().size())) {
            duration = duration + list.getDuration();
        }*/
        IMedia current = getCurrentFile();
        return "Playlist name : " + rootPlaylist.getName() +
                "; Total duration : " + duration + "; " + current.getInfos();
    }

    // COMMANDES

    public void setRootPlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new AssertionError("Paramètre invalide StdPlayerModel setCurrentPlaylist");
        }
        rootPlaylist = playlist;
    }

    public void setCurrentTime(int time) {
        if (time >= 0) {
            throw new AssertionError("Paramètre invalide StdPlayerModel setHeadDuration");
        }
        currentTime = time;
    }

    public void incrementDepth() {
        depth = depth + 1;
    }

    public void decrementDepth() {
        depth = depth - 1;
    }

    @Override
    public void load(File f) {
		String absolutePath = f.getParent() + "/";  
        manager = new XMLPlaylistManager(absolutePath);
        manager.load(f);
        rootPlaylist = manager.getPlaylist();
    }

    @Override
    public void play() {

        notifyObserversFile(getCurrentFile().getInfos());
    	timer = new Timer();
    	timer.schedule(new playFileTask(), 0, 1000);
    }

    @Override
    public void pause() {
    	timer.cancel();
    }

    @Override
    public void stop() {
        currentTime = 0;
        headPositions = new TreeMap<>();
        headPositions.put(0,0);
        depth = 0;
        timer.cancel();
    }

    @Override
    public void forward() {
    	
        int oldHeadPosition = headPositions.get(depth);
        SubList parentList = getParentCurrentFile();
        
    	// Si le fichier est le dernier de la liste
        if (oldHeadPosition == parentList.size() -1 ) {
        	//Si on est dans la liste racine, le player se stop
        	if(depth==0) {
        		stop();
        	}
        	//Sinon on remonte d'une profondeur, et on avance la tête de lecture
        	else {
        		headPositions.remove(depth);
                decrementDepth();
                forward();
        	}
        }
    	// Sinon on avance d'un fichier
    	else {
            headPositions.put(depth, oldHeadPosition + 1);
        	
            IMedia currentMedia = getCurrentFile();
            
            // Si l'entrée est une sous-liste,
            // le lecteur entre dans la sous-liste et commence la lecture de la première entrée
            if (currentMedia instanceof SubList) {
                while (currentMedia instanceof  SubList) {
                    incrementDepth();
                    headPositions.put(getDepth(), 0);
                    currentMedia = getCurrentFile();
                }
            }
            
            currentTime = 0;
            notifyObserversFile(getCurrentFile().getInfos());
    	}
    }

    @Override
    public void backward() {
    	
        int oldHeadPosition = headPositions.get(depth);
        
    	// Si le fichier est le premier de la liste
        if (oldHeadPosition == 0 && depth !=0) {
        	//Sinon on remonte d'une profondeur, et on avance la tête de lecture
        	headPositions.remove(depth);
            decrementDepth();
            backward();
        }
    	// Sinon on recule d'un fichier
    	else {
    		if (oldHeadPosition!=0) {
    			headPositions.put(depth, oldHeadPosition - 1);
    		}
    		
            IMedia currentMedia = getCurrentFile();
            
            // Si l'entrée est une sous-liste,
            // le lecteur entre dans la sous-liste et commence la lecture de la première entrée
            if (currentMedia instanceof SubList) {
                while (currentMedia instanceof  SubList) {
                    incrementDepth();
                    headPositions.put(getDepth(), ((SubList) currentMedia).size()-1);
                    currentMedia = getCurrentFile();
                }
            }
            
            currentTime = 0;
            notifyObserversFile(getCurrentFile().getInfos());
    	}
    }

    /**
     * Termine la sous-liste actuelle et démarre la lecture de l’entrée suivante dans la liste parent
     */
    @Override
    public void nextList() {

        SubList parentList = getParentCurrentFile();
        headPositions.put(getDepth(), parentList.size()-1);
        forward();
        
    }
    
    /**
     * Termine la sous-liste actuelle et revient à l’entrée précédente dans la liste parent.
     */
    @Override
    public void previousList() {

        headPositions.put(getDepth(), 0);
        backward();
        
    }
    

    public IMedia getCurrentFile() {
    	
    	SubList cursor = this.rootPlaylist.getPlaylist();
    	for (int k = 0; k < getDepth(); ++k) {
    		cursor = (SubList)cursor.getChild(headPositions.get(k));
    	}
    	
    	return cursor.getChild(headPositions.get(getDepth()));
    	
    	/*
        IMedia media = this.rootPlaylist.getPlaylist().getChild(headPositions.get(0));
        
        if (media instanceof SubList) {
            SubList sublist = (SubList) media;
            for (int k = 1; k <= getDepth(); ++k) {
                int head = headPositions.get(k);
                sublist = (SubList) sublist.getChild(head);
            }
            return sublist;
        }
        
        return media;*/
    }

    public SubList getParentCurrentFile() {

    	SubList cursor = this.rootPlaylist.getPlaylist();
    	for (int k = 0; k < getDepth(); ++k) {
    		cursor = (SubList)cursor.getChild(headPositions.get(k));
    	}
    	
    	return cursor;
    	
    	/*
        IMedia media = this.rootPlaylist.getPlaylist().getChild(headPositions.get(0));
        if (media instanceof SubList) {
            SubList sublist = (SubList) media;
            for (int k = 1; k < getDepth(); ++k) {
                int head = headPositions.get(k);
                sublist = (SubList) sublist.getChild(head);
            }
            return sublist;
        }
        return null;*/
    }

    public void incrementHeadDuration() {
        currentTime = currentTime + 1;
        notifyObserversTime(currentTime);
       
        if (currentTime == getCurrentFile().getDuration()) {
        	forward();
        }
        
    }

    /*CLASSES INTERNE*/
    public class playFileTask extends TimerTask {
		
    	@Override
		public void run() {
			//System.out.println(new Date() + " Execution de ma tache");
			incrementHeadDuration();
		}
    	
	}

}


