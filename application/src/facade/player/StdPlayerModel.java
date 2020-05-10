package facade.player;

import model.list.IListBuilder;
import model.list.IMedia;
import model.list.StdListBuilder;
import model.list.SubList;
import model.playlist.Playlist;
import model.xml.XMLPlaylistLoader;
import view.Observer;

import java.io.File;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class StdPlayerModel implements IPlayerModel, IPlayerSubject {

	private IObservable obs;
	
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
    
    /**
     * Timer utilisé pour simulé la lecture des fichiers.
     */
    private Timer timer;

   
    // CONSTRUCTEUR

    /**
     * Constructeur de la façade PlayerModel.
     * @param f : Fichier xpl contenant la playlist à lire.
     */
    public StdPlayerModel(File f) {
    	obs = new Observable();
        rootPlaylist = new Playlist();
        currentTime = 0;
        headPositions = new TreeMap<>();
        headPositions.put(0,0);
        depth = 0;
        timer = new Timer();
        load(f);
    }

    // METHODES

    @Override
	public Playlist getRootPlaylist() {
        return rootPlaylist;
    }

    @Override
	public int getCurrentTime() {
        return currentTime;
    }

    @Override
    public String getInfos() {
        IMedia current = getCurrentFile();
        return "Playlist name : " + rootPlaylist.getName() +
                "; Total duration : " + rootPlaylist.getPlaylist().getDuration() + 
                "; " + current.getInfos();
    }

    // COMMANDES

    @Override
	public void setRootPlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new AssertionError("Paramètre invalide StdPlayerModel setCurrentPlaylist");
        }
        rootPlaylist = playlist;
    }

    @Override
	public void setCurrentTime(int time) {
        if (time >= 0) {
            throw new AssertionError("Paramètre invalide StdPlayerModel setHeadDuration");
        }
        currentTime = time;
    }

    @Override
    public void load(File f) {        
    	if (f == null) {
    		throw new AssertionError("Paramètre invalide StdEditorModel load");
    	}
        IListBuilder playlistBuilder = new StdListBuilder(f);
        XMLPlaylistLoader.load(f, playlistBuilder);
        rootPlaylist = playlistBuilder.getPlaylist();
    }

    @Override
    public void play() {
    	timer = new Timer();
    	timer.schedule(new playFileTask(), 0, 1000);
        obs.notifyObservers();
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
                    headPositions.put(depth, 0);
                    currentMedia = getCurrentFile();
                }
            }
            
            currentTime = 0;
            obs.notifyObservers();
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
                    headPositions.put(depth, ((SubList) currentMedia).size()-1);
                    currentMedia = getCurrentFile();
                }
            }
            
            currentTime = 0;
            obs.notifyObservers();
    	}
    }

    @Override
    public void nextList() {

        SubList parentList = getParentCurrentFile();
        headPositions.put(depth, parentList.size()-1);
        forward();
        
    }
    
    @Override
    public void previousList() {

        headPositions.put(depth, 0);
        backward();
        
    }
    
    @Override
	public String getPathSubList() {
    	String ret = this.rootPlaylist.getName() + "/";
    	
    	SubList cursor = (SubList) this.rootPlaylist.getPlaylist();
    	for (int k = 0; k < depth; ++k) {
    		cursor = (SubList)cursor.getChild(headPositions.get(k));
        	ret += cursor.getName() + "/";
    	}
    	
    	return ret;
    }

    @Override
	public IMedia getCurrentFile() {
    	
    	SubList cursor = (SubList) this.rootPlaylist.getPlaylist();
    	for (int k = 0; k < depth; ++k) {
    		cursor = (SubList)cursor.getChild(headPositions.get(k));
    	}
    	
    	return cursor.getChild(headPositions.get(depth));
    }

    @Override
	public SubList getParentCurrentFile() {

    	SubList cursor = (SubList) this.rootPlaylist.getPlaylist();
    	for (int k = 0; k < depth; ++k) {
    		cursor = (SubList)cursor.getChild(headPositions.get(k));
    	}
    	
    	return cursor;
    }

    
    
    
    /**
     * Incrémente la profondeur du compteur de sous-liste de 1
     */
	private void incrementDepth() {
        depth = depth + 1;
    }
	
    /**
     * Décrémente la profondeur du compteur de sous-liste de 1
     */
    private void decrementDepth() {
        depth = depth - 1;
    }
    
    /**
     * Ajoute une seconde au temps passé.
     */
	private void incrementTime() {
        currentTime = currentTime + 1;

        if (currentTime == getCurrentFile().getDuration()) {
            forward();
        }
        obs.notifyObservers();
    }

    /*CLASSES INTERNE*/
    public class playFileTask extends TimerTask {
		
    	@Override
		public void run() {
			incrementTime();
		}
    	
	}
    
    public void attach ( Observer o)  { obs.attach(o); }
    public void dettach ( Observer o)  { obs.dettach(o); }

}


