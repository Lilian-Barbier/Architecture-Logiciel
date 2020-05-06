package model.playlist;

import model.list.IMedia;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    // ATTRIBUTS

    private List<IMedia> playlist;
    private String name;
    private int head;

    // CONSTRUCTEUR

    public Playlist() {
        playlist = new ArrayList<>();
        name = "new playlist";
        head = -1;
    }

    public Playlist(String name) {
        if (name == null) {
            throw new AssertionError("Paramètre invalide Playlist constructeur");
        }
        playlist = new ArrayList<>();
        name = "new playlist";
        head = -1;
    }

    // METHODES

    public List<IMedia> getPlaylist() {
        return playlist;
    }

    public int getHead() {
        return head;
    }

    public String getName() {
        return name;
    }

    public IMedia getCurrentFile() {
        if (head >= 0) {
            return playlist.get(head);
        }
        return null;
    }

    // COMMANDES

    public void setPlaylist(List<IMedia> playlist) {
        if (playlist == null) {
            throw new AssertionError("Paramètre invalide Playlist setPlaylist");
        }
        this.playlist = playlist;
    }

    public void setHead(int head) {
        this.head = head;
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
        this.playlist.addAll(list);
        if (list.size() > 0 && head == -1) {
            head = 0;
        }
    }

    public void addFile(IMedia list) {
        if (list == null) {
            throw new AssertionError("Paramètre invalide Playlist addFile");
        }
        this.playlist.add(list);
        if (head == -1) {
            head = 0;
        }
    }

    public void incrementHead() {
        head = head + 1;
    }

    public void decrementHead() {
        head = head - 1;
    }
}
