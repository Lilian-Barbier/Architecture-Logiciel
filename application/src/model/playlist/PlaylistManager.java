package model.playlist;

import model.list.ListBuilder;
import model.list.ListBuilderStd;

public class PlaylistManager implements IPlaylistManager {

    // ATTRIBUTS

    private Playlist playlist;
    private ListBuilder builder;

    // CONSTRUCTEUR

    public PlaylistManager() {
        playlist = new Playlist();
        builder = new ListBuilderStd();
    }

    public PlaylistManager(String name) {
        playlist = new Playlist(name);
        builder = new ListBuilderStd();
    }

    // METHODES

    @Override
    public Playlist getPlaylist() {
        return playlist;
    }

    @Override
    public ListBuilder getBuilder() {
        return builder;
    }

    // COMMANDES

    @Override
    public void setPlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new AssertionError("Paramètre invalide PlaylistManager setPlaylist");
        }
        this.playlist = playlist;
    }

    @Override
    public void setBuilder(ListBuilder builder) {
        if (builder == null) {
            throw new AssertionError("Paramètre invalide PlaylistManager setBuilder");
        }
        this.builder = builder;
    }
}
