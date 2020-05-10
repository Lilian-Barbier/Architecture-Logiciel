package model.list;

import model.playlist.Playlist;
import model.util.LoadAudio;
import model.util.LoadVideo;

import java.util.List;
import java.io.File;
import java.util.ArrayList;

public class StdListBuilder implements IListBuilder {

    // ATTRIBUTS

	/**
	 * Le répertoire des fichiers utilisés pour la construction de la list
	 */
	private final String filesFolder;

	/**
	 * La Playlist construite dans ce StdListBuilder
	 */
    private final Playlist playlist;

	/**
	 * Le type du IMedia en cours de construction
	 */
	private MediaType type;

	/**
	 * Le chemin du IMedia en cours de construction
	 */
	private String path;

	/**
	 * La profondeur du IMedia en cours de construction
	 */
	private int depthList;

	/**
	 * Enregistre la Sublist parent lorsque l'on plonge en profondeur
	 */
    private final List<List<IMedia>> subListsMedias;

	/**
	 * Enregistre le nom de la Sublist parent lorsque l'on plonge en profondeur
	 */
	private final List<String> subListsName;
    
    // CONSTRUCTEUR

    public StdListBuilder(Playlist playlist, String filesFolder) {
        if (playlist == null ) {
            throw new AssertionError("Paramètre invalide StdListBuilder constructeur");
        }
        this.filesFolder = filesFolder;
        this.playlist = playlist;
        this.type = MediaType.Nothing;
        this.subListsMedias = new ArrayList<>();
        this.depthList = 0;
        this.subListsName = new ArrayList<>();
    }

    public StdListBuilder(File fileLoad) {
    	this.filesFolder = fileLoad.getParent() + "/";  
        this.playlist = new Playlist(fileLoad.getName());
        this.type = MediaType.Nothing;
        this.subListsMedias = new ArrayList<>();
        this.depthList = 0;
        this.subListsName = new ArrayList<>();
    }

    // METHODES

	@Override
	public Playlist getPlaylist() {
        return playlist;
    }

	/**
	 * Renvoie le type du IMedia en cours de construction
	 * @return this.type
	 */
	public MediaType getType() {
		return type;
	}

	/**
	 * Renvoie la profondeur du IMedia en cours de construction
	 * @return this.depthList
	 */
	public int getDepthList() {
		return depthList;
	}

	/**
	 * Renvoie le chemin du fichier en cours de création
	 * @return this.path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Renvoie le nom de la Sublist parent lorsque l'on plonge en profondeur
	 * @return this.subListsName
	 */
	public List<String> getSubListsName() {
		return subListsName;
	}

	/**
	 * Renvoie la Sublist parent lorsque l'on plonge en profondeur
	 * @return this.subListsMedias
	 */
	public List<List<IMedia>> getSubListsMedias() {
		return subListsMedias;
	}

	// COMMANDES


	public void setDepthList(int depthList) {
		this.depthList = depthList;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setType(MediaType type) {
		this.type = type;
	}

	@Override
    public void startSublist() {
    	if (getType() != MediaType.Nothing && getType() != MediaType.Sublist ) {
    		//TODO Exception
    		System.out.println("StartAudio : Type not valid ("+ this.type +")");
    	}
    	setType(MediaType.Sublist);
    	setDepthList(getDepthList() + 1);
    	getSubListsMedias().add(new ArrayList<>());
    }

    @Override
    public void stopSublist() {
    	if (getType() != MediaType.Sublist) {
      		System.out.println("stopSublist : Type not valid ("+ this.type +")");
    	}
    	if (getSubListsMedias().size() != getDepthList() && getDepthList() >= 0) {
    		//TODO Exception
    		System.out.println("addName : subListsMedias size not valid");
    	}
    	SubList s = new SubList(getSubListsMedias().get(getDepthList() - 1), getSubListsName().get(getDepthList() - 1));
    	setType(MediaType.Nothing);
    	getSubListsMedias().remove(getDepthList() - 1);
    	getSubListsName().remove(getDepthList() - 1);
    	setDepthList(getDepthList() - 1);
    	if (getDepthList() > 0) {
    		getSubListsMedias().get(getDepthList() - 1).add(s);
    		setType(MediaType.Sublist);
    	} else {
    		getPlaylist().addFile(s);
    		setType(MediaType.Nothing);
    	}
	}

    @Override
    public void startAudio() {    	
    	//Un audio ne peut pas être dans une video
    	if (getType() != MediaType.Nothing && getType() != MediaType.Sublist ) {
    		//TODO Exception
    		System.out.println("StartAudio : Type not valid ("+ this.type +")");
    	}
    	setType(MediaType.Audio);
    	setPath(null);
    }

    @Override
    public void stopAudio() {
    	if (getType() != MediaType.Audio) {
    		//TODO Exception
    		System.out.println("StopAudio : Type not valid ("+ this.type +")");
    	}
    	if (getPath() == null){
    		//TODO Exception
    		System.out.println("StopAudio : Path Null");
    	}
    	IMedia a = new LoadAudio().loadFile(filesFolder + getPath());
    	//Si cet audio est contenu dans une sous-liste
    	if(getDepthList() > 0) {
    		getSubListsMedias().get(getDepthList() - 1).add(a);
        	setType(MediaType.Sublist);
		//Si il est contenu dans le Media principal
		} else {
    		getPlaylist().addFile(a);
        	setType(MediaType.Nothing);
    	}
    }

    @Override
    public void startVideo() {    	
    	if (getType() != MediaType.Nothing && getType() != MediaType.Sublist ) {
    		//TODO Exception
    		System.out.println("StartVideo : Type not valid");
    	}
    	setType(MediaType.Video);
    	setPath(null);
    }

    @Override
    public void stopVideo() {
    	if (getType() != MediaType.Video || getPath() == null) {
			//TODO Exception
			System.out.println("StopVideo : Type not valid");
		}
    	IMedia v = new LoadVideo().loadFile(filesFolder + getPath());
    	//Si cette video est contenu dans une sous-liste
    	if (getDepthList() > 0) {
    		getSubListsMedias().get(getDepthList() - 1).add(v);
        	setType(MediaType.Sublist);
		//Si elle est contenu dans le Media principal
    	} else {
    		getPlaylist().addFile(v);
        	setType(MediaType.Nothing);
    	}
    }

    @Override
    public void addName(String name) {
    	if (getType() != MediaType.Sublist) {
    		//TODO Exception
    		System.out.println("addName : Type not valid ("+ this.type +")");
    	}
    	if (getSubListsMedias().size() != getDepthList()) {
    		//TODO Exception
    		System.out.println("addName : subListsMedias size not valid");
    	}
    	getSubListsName().add(name);
    }
    
    @Override
    public void addPath(String path) {
    	if (getPath() != null || getType() == MediaType.Sublist) {
    		//TODO Exception
			System.out.println("addPath : Type not valid ("+ this.type +") or path == null (" + this.path +")" );
		}
    	setPath(path);
    }
}
