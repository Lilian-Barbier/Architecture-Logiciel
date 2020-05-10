package model.playlist;

import model.list.IMedia;
import model.list.SubList;

import java.util.List;

@SuppressWarnings("unused")
public class Playlist implements IPlaylist {

    // ATTRIBUTS

    /**
     * L'ensemble des IMedia contenus dans cette Playlist
     */
	private IMedia playlist;

    /**
     * Le nom de la Playlist
     */
    private String name;

    // CONSTRUCTEUR

    public Playlist() {
    	playlist = new SubList();
        name = "new playlist";
    }

    public Playlist(String name) {
        if (name == null) {
            throw new AssertionError("Paramètre invalide Playlist constructeur");
        }
        playlist = new SubList(0,name);
        //playlist = new ArrayList<>();
        this.name = name;
    }

    // METHODES

    public IMedia getPlaylist() {
    	return playlist;
    }

    public String getName() {
        return name;
    }

    // COMMANDES

    public void setPlaylist(SubList playlist) {
        if (playlist == null) {
            throw new AssertionError("Paramètre invalide Playlist setPlaylist");
        }
        this.playlist = playlist;
    }

    public void setName(String name) {
        if (name == null) {
            throw new AssertionError("Paramètre invalide Playlist setName");
        }
        this.name = name;
    }

    public void addList(List<IMedia> list) {
        if (list == null) {
            throw new AssertionError("Paramètre invalide Playlist addList");
        }
        SubList sublist = (SubList) this.getPlaylist();
        for(IMedia m : list) {
            sublist.add(m);
        }
    }

    public void addFile(IMedia media) {
        if (media == null) {
            throw new AssertionError("Paramètre invalide Playlist addFile");
        }
        SubList sublist = (SubList) this.getPlaylist();
        sublist.add(media);
    }
}
