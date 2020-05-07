package model.playlist;

import model.list.IMedia;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Playlist implements IPlaylist {

    // ATTRIBUTS

    /**
     * L'ensemble des IMedia contenus dans cette Playlist
     */
    private List<IMedia> playlist;

    /**
     * Le nom de la Playlist
     */
    private String name;

    // CONSTRUCTEUR

    public Playlist() {
        playlist = new ArrayList<>();
        name = "new playlist";
    }

    public Playlist(String name) {
        if (name == null) {
            throw new AssertionError("Paramètre invalide Playlist constructeur");
        }
        playlist = new ArrayList<>();
        this.name = name;
    }

    // METHODES

    public List<IMedia> getPlaylist() {
        return playlist;
    }

    public String getName() {
        return name;
    }

    // COMMANDES

    public void setPlaylist(List<IMedia> playlist) {
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

    /**
     * Ajoute à la playlist l'ensemble de IMedia passé en paramètre
     * @param list l'ensemble de IMedia à ajouter à la playlist
     */
    public void addList(List<IMedia> list) {
        if (list == null) {
            throw new AssertionError("Paramètre invalide Playlist addList");
        }
        this.getPlaylist().addAll(list);
    }

    /**
     * Ajoute à la playlist le IMedia passé en paramètre
     * @param media le IMedia à ajouter à la playlist
     */
    public void addFile(IMedia media) {
        if (media == null) {
            throw new AssertionError("Paramètre invalide Playlist addFile");
        }
        this.getPlaylist().add(media);
    }
}
