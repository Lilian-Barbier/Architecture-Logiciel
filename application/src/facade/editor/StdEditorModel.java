package facade.editor;

import model.list.*;
import model.playlist.IPlaylist;
import model.playlist.IPlaylistManager;
import model.playlist.Playlist;
import model.playlist.PlaylistManager;
import model.util.LoadFiles;
import model.xml.XMLPlaylistManager;

import java.io.*;
import java.util.Map;

@SuppressWarnings("unused")
public class StdEditorModel implements IEditorModel {

    // ATTRIBUTS
    

    
    /**
     * Le manager de fichier associé au StbEditorModel
     */
    private IPlaylistManager manager;

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
    
    

    // CONSTRUCTEUR

    public StdEditorModel() {
    	rootPlaylist = new Playlist();
    }

    // METHODES

    IPlaylist getCurrentPlaylist() {
        return rootPlaylist;
    }


    @Override
    public String getInfos() {
        int duration = 0;
        for (IMedia list : getCurrentPlaylist().getPlaylist().subList(0, getCurrentPlaylist().getPlaylist().size())) {
            duration = duration + list.getDuration();
        }
        return "playlist name = " + getCurrentPlaylist().getName() +
                " total duration " + duration;
    }

    // COMMANDES

   /*void setCurrentPlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new AssertionError("Paramètre invalide StdEditorModel setCurrentPlaylist");
        }        currentPlaylist = playlist;
    }*/

    @Override
    public void create(String name) {
        if (name == null) {
            throw new AssertionError("Paramètre invalide StdEditorModel create");
        }
        getCurrentPlaylist().setName(name);
    }

    @Override
    public void load(File f) {
        if (f == null) {
            throw new AssertionError("Paramètre invalide StdEditorModel load");
        }
        String absolutePath = f.getParent() + "/";
        manager = new XMLPlaylistManager(absolutePath);
        manager.load(f);
        currentPlaylist = manager.getPlaylist();
    }

    @Override
    public void save() {
        manager.save();
    }

    @Override
    public void addFile(String path) throws IOException {
        if (path == null) {
            throw new AssertionError("Paramètre invalide StdEditorModel addFile");
        }
        BufferedReader lecteurAvecBuffer = null;
        IMedia list = new LoadFiles().loadFile(path);
        try {
            lecteurAvecBuffer = new BufferedReader(new FileReader(path));
            list.setDuration(Integer.parseInt(lecteurAvecBuffer.readLine()));
            list.setName(lecteurAvecBuffer.readLine());
            list.setPath(path);
        } catch(FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture");
        } finally {
            lecteurAvecBuffer.close();
        }
        getCurrentPlaylist().addFile(list);
    }

    @Override
    public void addFilesFromFolder(String path) throws IOException {
        File f = new File(path);
        if (f.isDirectory()){
            File[] tab = f.listFiles();
            for (int i = 0; i != tab.length; i++){
                System.out.println(tab[i].getAbsolutePath());
                if (tab[i].isDirectory()){
                    addFilesFromFolder(tab[i].getAbsolutePath());
                } else {
                    addFile(tab[i].getAbsolutePath());
                }
            }
        }
    }

    @Override
    public void addList(String path) throws IOException {
        if (path == null) {
            throw new AssertionError("Paramètre invalide StdEditorModel addList");
        }
        BufferedReader lecteurAvecBuffer = null;
        IMedia list = new SubList();
        try {
            lecteurAvecBuffer = new BufferedReader(new FileReader(path));
            list.setDuration(Integer.parseInt(lecteurAvecBuffer.readLine()));
            list.setName(lecteurAvecBuffer.readLine());
            list.setPath(path);
        } catch(FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture");
        } finally {
            if (lecteurAvecBuffer != null) {
                lecteurAvecBuffer.close();
            }
        }
        getCurrentPlaylist().addFile(list);
    }

	public void enterList() {
		// TODO Auto-generated method stub
		
	}

	public void ascendList() {
		// TODO Auto-generated method stub
		
	}
}
