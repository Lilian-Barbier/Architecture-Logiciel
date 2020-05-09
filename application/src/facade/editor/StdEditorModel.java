package facade.editor;

import model.list.IListBuilder;
import model.list.IMedia;
import model.list.StdListBuilder;
import model.list.SubList;
import model.playlist.IPlaylist;
import model.playlist.Playlist;
import model.util.LoadAudio;
import model.util.LoadVideo;
import model.xml.XMLPlaylistLoader;
import model.xml.XMLPlaylistSaver;

import java.io.*;
import java.util.Map;

public class StdEditorModel implements IEditorModel {

    //  CONSTANTES
    private static final String ERROR = "";
    private static final String MTA = "mta";
    private static final String MTV = "mtv";
    private static final String SPLIT = ".";


    // ATTRIBUTS

    /**
     * L'objet Playlist est la racine de notre playlist.
     */
    private Playlist rootPlaylist;

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

    IPlaylist getRootPlaylist() {
        return rootPlaylist;
    }

    public int getDepth() { return depth; }

    @Override
    public String getInfos() {
        int duration = 0;
        /*for (IMedia list : getCurrentPlaylist().getPlaylist().subList(0, getCurrentPlaylist().getPlaylist().size())) {
            duration = duration + list.getDuration();
        }*/
        return "playlist name = " + getRootPlaylist().getName() +
                " total duration " + duration;
   }

    // COMMANDES

    void setRootPlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new AssertionError("Paramètre invalide StdEditorModel setCurrentPlaylist");
        }        rootPlaylist = playlist;
    }

    @Override
    public void create(String name) {
        if (name == null) {
            throw new AssertionError("Paramètre invalide StdEditorModel create");
        }
        getRootPlaylist().setName(name);
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
    public void save() {
    	XMLPlaylistSaver.save(this.getRootPlaylist());
    }

    @Override
    public void addFile(String path) throws IOException {
        if (path == null) {
            throw new AssertionError("Paramètre invalide StdEditorModel addFile");
        }
        BufferedReader br = null;
        String[] splitter = path.split(SPLIT);
        String extension = splitter[splitter.length - 1 ];
        IMedia media = null;
        switch (extension) {
            case ERROR :
                throw new AssertionError("Format non reconnu");
            case MTA :
                media = new LoadAudio().loadFile(path);
            case MTV :
                media = new LoadVideo().loadFile(path);
        }
        try {
            br = new BufferedReader(new FileReader(path));
            if (media != null) {
                media.setDuration(Integer.parseInt(br.readLine()));
                media.setName(br.readLine());
                media.setPath(path);
            }
        } catch(FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture");
        } finally {
            if (br != null) {
                br.close();
            }
        }
        SubList cursor = (SubList) this.rootPlaylist.getPlaylist();
        for (int k = 0; k < getDepth(); ++k) {
            cursor = (SubList)cursor.getChild(headPositions.get(k));
        }
        cursor.add(media);
    }

    @Override
    public void addFilesFromFolder(String path) throws IOException {
        File f = new File(path);
        if (f.isDirectory()){
            File[] tab = f.listFiles();
            if (tab != null) {
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
    }

    @Override
    public void addList(String path) {
        if (path == null) {
            throw new AssertionError("Paramètre invalide StdEditorModel addList");
        }
        File f = new File(path);
        
        IListBuilder playlistBuilder = new StdListBuilder(f);
        XMLPlaylistLoader.load(f, playlistBuilder);
        SubList sublist = (SubList) playlistBuilder.getPlaylist().getPlaylist();
        SubList cursor = (SubList) this.rootPlaylist.getPlaylist();
        for (int k = 0; k < getDepth(); ++k) {
            cursor = (SubList)cursor.getChild(headPositions.get(k));
        }
        cursor.add(sublist);
    }

    public void incrementDepth() {
        depth = depth + 1;
    }

    public void decrementDepth() {
        depth = depth - 1;
    }

	public void enterList(int index) {
        incrementDepth();
        headPositions.put(getDepth(), index);
	}

	public void ascendList() {
        headPositions.remove(getDepth());
        decrementDepth();
	}
}

