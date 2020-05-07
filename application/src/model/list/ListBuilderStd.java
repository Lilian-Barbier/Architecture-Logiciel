package model.list;

import model.playlist.Playlist;

import java.util.List;
import java.util.ArrayList;
;

public class ListBuilderStd implements ListBuilder {

    // ATTRIBUTS
    private Playlist playlist;
    private MediaType type;
    
    private String path;
    private List<IMedia> subList;
    private String name;
    
    private int depthList;
    private List<List<IMedia>> subListsMedias;
    private List<String> subListsName;
    
    // CONSTRUCTEUR
    public ListBuilderStd(Playlist playlist) {
        if (playlist == null) {
            throw new AssertionError("Paramètre invalide ListBuilderStd constructeur");
        }
        this.playlist = playlist;
        this.type = MediaType.Nothing;
        
        this.subListsMedias = new ArrayList<List<IMedia>>();
        this.depthList = 0;
        this.subListsName = new ArrayList<String>();
    }

    public ListBuilderStd() {
        this.playlist = new Playlist();
        this.type = MediaType.Nothing;
        
        this.subListsMedias = new ArrayList<List<IMedia>>();
        this.depthList = 0;
        this.subListsName = new ArrayList<String>();
    }

    // METHODES

    public Playlist getPlaylist() {
        return playlist;
    }

    // COMMANDES

    @Override
    public void startSublist() {    	
    	
    	if(this.type != MediaType.Nothing && this.type != MediaType.Sublist ) {
    		//TODO Exception
    		System.out.println("StartAudio : Type not valid ("+ this.type +")");
    	}	
    	
    	this.type = MediaType.Sublist;
    	
    	depthList++;
    	this.subListsMedias.add(new ArrayList<IMedia>());
    }

    
    @Override
    public void stopSublist() {
		System.out.println("test");
    	if(this.type != MediaType.Sublist) {
      		System.out.println("stopSublist : Type not valid ("+ this.type +")");
    	}
    	if(this.subListsMedias.size() != depthList && depthList>=0) {
    		//TODO Exception
    		System.out.println("addName : subListsMedias size not valid");
    	}
    	
    	
    	SubList s = new SubList(subListsMedias.get(depthList-1), subListsName.get(depthList-1));
    	
    	type = MediaType.Nothing;
    	this.name = null;
    	
    	subListsMedias.remove(depthList-1);
    	subListsName.remove(depthList-1);
    	depthList--;
    	
    	if(depthList > 0) {
    		subListsMedias.get(depthList-1).add(s);
    		type = MediaType.Sublist;
    	}
    	else {
    		this.playlist.addFile(s);
    		type = MediaType.Nothing;
    	}
		s.setDepth(depthList);


	}

    @Override
    public void startAudio() {    	
    	//Un audio ne peut pas être dans une video
    	if(this.type != MediaType.Nothing && this.type != MediaType.Sublist ) {
    		//TODO Exception
    		System.out.println("StartAudio : Type not valid ("+ this.type +")");
    	}
    	
    	this.type = MediaType.Audio;
    	this.path = null;
    }

    @Override
    public void stopAudio() {
    	if(this.type != MediaType.Audio) {
    		//TODO Exception
    		System.out.println("StopAudio : Type not valid ("+ this.type +")");
    	}
    	if(this.path == null){
    		//TODO Exception
    		System.out.println("StopAudio : Path Null");
    	}
    	
    	Audio a = new Audio(path);
    	
    	//Si cet audio est contenus dans une sous-liste
    	if(depthList > 0) {
    		subListsMedias.get(depthList-1).add(a);
        	type = MediaType.Sublist;
    	}
    	//Si il est contenus dans la Media Principale
    	else {
    		playlist.addFile(a);
        	type = MediaType.Nothing;
    	}
    	
    }

    @Override
    public void startVideo() {    	
    	if(this.type != MediaType.Nothing && this.type != MediaType.Sublist ) {
    		//TODO Exception
    		System.out.println("StartVideo : Type not valid");
    	}
    	
    	type = MediaType.Video;
    	path = null;
    }

    @Override
    public void stopVideo() {
    	if(this.type != MediaType.Video || this.path == null) {
    		//TODO Exception
    		System.out.println("StopVideo : Type not valid");
    	}
    	
    	Video v = new Video(path);
    	
    	//Si cette video est contenus dans une sous-liste
    	if(depthList > 0) {
    		subListsMedias.get(depthList-1).add(v);
        	type = MediaType.Sublist;
    	}
    	//Si elle est contenus dans la Media Principale
    	else {
    		playlist.addFile(v);
        	type = MediaType.Nothing;
    	}
    }

    @Override
    public void addName(String name) {
    	if(this.type != MediaType.Sublist) {
    		//TODO Exception
    		System.out.println("addName : Type not valid ("+ this.type +")");
    	}
    	if(this.subListsMedias.size() != depthList) {
    		//TODO Exception
    		System.out.println("addName : subListsMedias size not valid");
    	}
    	
        this.subListsName.add(name);
    }
    
    @Override
    public void addPath(String path) {
    	if(this.path != null || this.type == MediaType.Sublist) {
    		//TODO Exception
    	}
    	this.path = path;
    }
    
}
