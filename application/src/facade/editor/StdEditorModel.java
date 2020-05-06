package facade.editor;

import model.list.*;
import model.playlist.IPlaylistManager;
import model.playlist.Playlist;
import model.playlist.PlaylistManager;
import model.xml.XMLPlaylistManager;

import java.io.*;

public class StdEditorModel implements IEditorModel {

    // ATTRIBUTS

    private IPlaylistManager manager;
    private Playlist currentPlaylist;
    private Playlist parentPlaylist;

    // CONSTRUCTEUR

    public StdEditorModel() {
        manager = new PlaylistManager();
        currentPlaylist = new Playlist();
        parentPlaylist = new Playlist();
    }

    // METHODES

    Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    Playlist getParentPlaylist() {
        return parentPlaylist;
    }

    @Override
    public String getInfos() {
        int duration = 0;
        for (IMedia list : currentPlaylist.getPlaylist().subList(0, currentPlaylist.getPlaylist().size())) {
            duration = duration + list.getDuration();
        }
        IMedia current = currentPlaylist.getCurrentFile();
        return "playlist name = " + currentPlaylist.getName() +
                " total duration " + duration;
    }

    @Override
    public void getChild() {

    }

    @Override
    public void getParent() {

    }

    // COMMANDES

    void setCurrentPlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new AssertionError("Paramètre invalide StdEditorModel setCurrentPlaylist");
        }        currentPlaylist = playlist;
    }

    void setParentPlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new AssertionError("Paramètre invalide StdEditorModel setParentPlaylist");
        }
        parentPlaylist = playlist;
    }

    @Override
    public void create(String name) {
        if (name == null) {
            throw new AssertionError("Paramètre invalide StdEditorModel creates");
        }
        currentPlaylist.setName(name);
    }

    @Override
    public void load(File f) {
        manager.load(f);
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
        String ligne;
        IMedia list = new Media();
        try {
            lecteurAvecBuffer = new BufferedReader(new FileReader(path));
            list.setDuration(Integer.parseInt(lecteurAvecBuffer.readLine()));
            list.setName(lecteurAvecBuffer.readLine());
            list.setPath(path);
        }
        catch(FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture");
        } finally {
            lecteurAvecBuffer.close();
        }
        currentPlaylist.addFile(list);
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
            throw new AssertionError("Paramètre invalide StdEditorModel addFile");
        }
        BufferedReader lecteurAvecBuffer = null;
        String ligne;
        IMedia list = new SubList();
        try {
            lecteurAvecBuffer = new BufferedReader(new FileReader(path));
            list.setDuration(Integer.parseInt(lecteurAvecBuffer.readLine()));
            list.setName(lecteurAvecBuffer.readLine());
            list.setPath(path);
        }
        catch(FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture");
        } finally {
            lecteurAvecBuffer.close();
        }
        currentPlaylist.addFile(list);
    }
}
